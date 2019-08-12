package com.sunkaisens.isolated.module.service.impl;

import java.net.SocketException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunkaisens.isolated.module.domain.ModuleParas;
import com.sunkaisens.isolated.module.domain.MsgPacket;
import com.sunkaisens.isolated.module.properties.UdpCommProperties;
import com.sunkaisens.isolated.module.service.UdpNetService;
import com.sunkaisens.isolated.module.thread.UdpNetComm;

import lombok.extern.slf4j.Slf4j;

/**
 * UDP通信实现
 *
 * @author Reneryan
 */
@Slf4j
@Service("udpNetService")
public class UdpNetServiceImpl implements UdpNetService {

	@Autowired
	private UdpCommProperties udpCommProperties;
	
	@Autowired
	private UdpNetComm udpNetComm;

	/**
	 * <p>
	 * Title: initUdpComm
	 * </p>
	 * <p>
	 * Description: 初始化UDP通信
	 * </p>
	 * 
	 * @return
	 * @see com.sunkaisens.isolated.module.service.UdpNetService#initUdpComm()
	 */
	@Override
	public boolean initUdpComm() {

		log.info("初始化接收UDP消息");
		// 初始化socket监听的IP和Port
		try {
			udpNetComm.initNetComm(udpCommProperties.getLocalIp(), Integer.valueOf(udpCommProperties.getLocalPort()));
			return true;
		} catch (SocketException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	/**   
	 * <p>Description: 下发配置消息</p>   
	 * @param packet   
	 * @see com.sunkaisens.isolated.module.service.UdpNetService#sendMsg(com.sunkaisens.isolated.module.domain.MsgPacket)   
	 */
	@Override
	public void sendMsg(ModuleParas moduleParas) {
		MsgPacket packet = new MsgPacket();
		packet.setMsgPayload(moduleParas.toMsgString());
		udpNetComm.send(packet, udpCommProperties.getRemoteIp(), Integer.valueOf(udpCommProperties.getRemotePort()));
	}
	
}
