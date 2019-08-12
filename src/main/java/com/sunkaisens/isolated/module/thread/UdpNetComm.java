package com.sunkaisens.isolated.module.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sunkaisens.isolated.module.domain.ModuleParas;
import com.sunkaisens.isolated.module.domain.MsgPacket;
import com.sunkaisens.isolated.module.service.ModuleParasService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 接收消息的内容 
 * @author Eryan
 */
@Slf4j
@Data
@Component
public class UdpNetComm implements Runnable {
	
	public static final int MAX_UDP_PACKET_LEN = MsgPacket.MSG_LEN_MAX; //4096;
	public static DatagramSocket socket;
	private String socketName;
	private String localIP;
	private int localPort;
	private Thread thread;
	
//	private volatile static UdpNetComm udpNetComm;
	private static ThreadPoolExecutor executor;
	
	@Autowired
	private ModuleParasService moduleParasService;
	
//	MsgPacket msgPacket;
	
//	public static ConcurrentHashMap<String, MessageProcessThread[]> messageProcessThreadMap = new ConcurrentHashMap<String, MessageProcessThread[]>();
//	
//	//存储收到的消息
//	public static BlockingQueue<SubMsgPacket> receivedMsgQueue = new LinkedBlockingQueue<SubMsgPacket>();
	
//	private static final int dbThreadCount = 8;
//	private static final int maxRevMsgCount = 65535;
//	private int dbMsgCount=0;
	//接收目的地址
//	private int descTypeInt = Integer.valueOf(Integer.toHexString(CncpProtoType.NI_HEADER),16);
	
	public void initNetComm(String localIP, int localPort) throws SocketException {
		log.info("Init udp net communication: localIP: " + localIP + ", localPort: " + localPort);
		this.localIP = localIP;
		this.localPort = localPort;
		socket = new DatagramSocket(localPort);
		
		executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		initUDPNetComms();
	}
	
	private void initUDPNetComms() {
		createThread();
	}

	private void createThread() {
		thread = new Thread(this, "UDPNetCommThread");
		thread.setDaemon(true);
		thread.start();
	}
	
	/*******************************************************
	表1.1 UI接口字段描述表
	Key				Value							说明						M/C
	MAccUIMsg	UIToSys/SysToUI						消息流向					M
	Module		FT/GL/Master/Slave				FT主机/隔离卡/主通信板/从通信板		M
	Function	Status/SysConfig/service/Log	状态显示/系统配置/专用服务/日志		M
	SubFunction	HardStatus/linkStatus			硬件状态/链路状态					C(status时携带)
				NetConfig/ContactConfig			网络配置/联系人配置				C(sysconfig时携带)
				LinkTest/ServiceTest			链路测试/业务测试					C(service时携带)
				
	注：M为必带项，C为选择携带项
	
	示例如下：
		MAccUIMsg:SysToUI/Module:Master/Function:Status/SubFunction:hardStatus/hardName:Master/HardVersion:1.0.1
	*/
	@Override
	public void run() {
		log.debug(UdpNetComm.class.getSimpleName() + " is runing...");
		while(true){
			try {
				DatagramPacket revPacket = new DatagramPacket(new byte[MAX_UDP_PACKET_LEN], MAX_UDP_PACKET_LEN);
				socket.receive(revPacket);
				
				byte[] buf = revPacket.getData();
				MsgPacket msgPacket = new MsgPacket();
				
				msgPacket.setIpAddress(revPacket.getAddress().getHostAddress());
				msgPacket.setPort(revPacket.getPort());

				String msgPayload = new String(buf, 0, revPacket.getLength(), Charset.forName("UTF-8"));
				msgPacket.setMsgPayload(msgPayload);
				log.debug("Received a message: " + msgPayload);
				
				MessageProcessThread messageProcessThread = new MessageProcessThread();
				messageProcessThread.setMsgPacket(msgPacket);
				
				executor.execute(messageProcessThread);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	@Data
	class MessageProcessThread implements Runnable {
		MsgPacket msgPacket;

		@Override
		public void run() {
			if(this.msgPacket == null) {
				log.error("消息包实体为空！");
				return;
			}
			log.info("MessageProcessThread: " + this.msgPacket.getMsgPayload());
			
			ModuleParas moduleParas = msgPacket.toModuleParas();
			moduleParasService.saveOrUpdate(moduleParas);
		}
	}
	
	/**
	 * 发送UDP消息 
	 */
	public synchronized void send(MsgPacket pack, String remoteIP, int remotePort) {
		try {
			byte[] msgBytes = pack.getMsgBytes();
			if(msgBytes != null) {
				socket.send(new DatagramPacket(msgBytes, msgBytes.length, InetAddress.getByName(remoteIP), remotePort));
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
