package com.sunkaisens.isolated.module.domain;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.sunkaisens.isolated.module.constant.IsolatedCommProtoType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class MsgPacket extends UdpPacket{
	public static final int MSG_LEN_MAX = 1450;
	
//	private int msgLen;
	private String msgPayload;
	
	public MsgPacket() {
		log.debug("New a Msgpacket...");
	}

	private static boolean validate(String msgPayload) {
		String msgPayloadLower = msgPayload.toLowerCase();
		if (!msgPayloadLower.contains(IsolatedCommProtoType.MSG_KEY_MACCUIMSG.toLowerCase())
				|| !msgPayloadLower.contains(IsolatedCommProtoType.MSG_KEY_MODULE.toLowerCase())
				|| !msgPayloadLower.contains(IsolatedCommProtoType.MSG_KEY_FUNCTION.toLowerCase())) {
			log.error("msgPayload is invalid, not find the following key: " + IsolatedCommProtoType.MSG_KEY_MACCUIMSG
					+ " or " + IsolatedCommProtoType.MSG_KEY_MODULE + " or " + IsolatedCommProtoType.MSG_KEY_FUNCTION);
			return false;
		}
		return true;

	}
	
	private static boolean isTableField(String msgKey) {
		if (msgKey.equalsIgnoreCase(IsolatedCommProtoType.MSG_KEY_MACCUIMSG)
				|| msgKey.equalsIgnoreCase(IsolatedCommProtoType.MSG_KEY_MODULE)
				|| msgKey.equalsIgnoreCase(IsolatedCommProtoType.MSG_KEY_FUNCTION)
				|| msgKey.equalsIgnoreCase(IsolatedCommProtoType.MSG_KEY_SUBFUNCTION)) {
			return true;
		}
		return false;
	}
	
	/**   
	 * @return
	 * @return MsgContentServiceImpl
	 */
	public ModuleParas toModuleParas() {
		if(StringUtils.isBlank(this.msgPayload)) {
			log.error("MsgPayload is blank.");
			return null;
		}
		
		if(!validate(this.msgPayload)) {
			return null;
		}
		
		ModuleParas moduleParas = new ModuleParas();
		HashMap<String, String> msgFields = new LinkedHashMap<String, String>();
		
		String[] msgPayloadArray = this.msgPayload.split("/");
		for(String msg: msgPayloadArray) {
			if(msg.contains(":")) {
				String[] msgField = msg.split(":");
				msgFields.put(isTableField(msgField[0]) ? msgField[0].toLowerCase() : msgField[0], msgField[1]);
			}
		}
		
		moduleParas.setMAccUiMsg(msgFields.remove(IsolatedCommProtoType.MSG_KEY_MACCUIMSG.toLowerCase()));
		moduleParas.setModule(msgFields.remove(IsolatedCommProtoType.MSG_KEY_MODULE.toLowerCase()));
		moduleParas.setFunction(msgFields.remove(IsolatedCommProtoType.MSG_KEY_FUNCTION.toLowerCase()));

		moduleParas.setParaId(/*moduleParas.getMAccUiMsg() + "-" + */moduleParas.getModule() + "-" + moduleParas.getFunction());
		if(msgFields.containsKey(IsolatedCommProtoType.MSG_KEY_SUBFUNCTION.toLowerCase())) {
			moduleParas.setSubFunction(msgFields.remove(IsolatedCommProtoType.MSG_KEY_SUBFUNCTION.toLowerCase()));
			moduleParas.setParaId(moduleParas.getParaId() + "-" + moduleParas.getSubFunction());
		}
		
		moduleParas.setMsgParas(new JSONObject(msgFields).toString());
		
		return moduleParas;
	}

	/** 
	 * 将msgPayload转换为bytes，UTF-8字符集。  
	 * @return byte []
	 */
	public byte[] getMsgBytes() {
		try {
			return this.msgPayload.getBytes(IsolatedCommProtoType.MSG_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
//	public String toMsgString(ModuleParas moduleParas) {
//		String msgString = moduleParas.toMsgString();
//		return msgString;
//	}

}
