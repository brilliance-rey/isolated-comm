package com.sunkaisens.isolated.module.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sunkaisens.isolated.module.constant.IsolatedCommProtoType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 模块参数实体类
 * @author Reneryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ModuleParas implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "para_id", type = IdType.INPUT)
    private String paraId;

    /**
     * 消息流向
     */
    private String mAccUiMsg;

    /**
     * 通信模块: FT主机、隔离卡、主通信卡、从通信卡
     */
    private String module;

    /**
     * 通信功能：状态显示/系统配置/专用服务/日志
     */
    private String function;

    /**
     * 通信子功能：硬件状态/链路状态, 网络配置/联系人配置, 链路测试/业务测试
     */
    private String subFunction;

    /**
     * 消息参数
     */
    private String msgParas;

    public String toMsgString() {
    	StringBuffer sBuffer = new StringBuffer();
    	sBuffer.append(IsolatedCommProtoType.MSG_KEY_MACCUIMSG).append(IsolatedCommProtoType.MSG_KEY_VALUE_CONNECTOR).append(this.mAccUiMsg);
    	sBuffer.append(IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
    	sBuffer.append(IsolatedCommProtoType.MSG_KEY_MODULE + IsolatedCommProtoType.MSG_KEY_VALUE_CONNECTOR + this.module);
    	sBuffer.append(IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
    	sBuffer.append(IsolatedCommProtoType.MSG_KEY_FUNCTION + IsolatedCommProtoType.MSG_KEY_VALUE_CONNECTOR + this.function); 
		if(!StringUtils.isBlank(this.subFunction)) {
			sBuffer.append(IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
			sBuffer.append(IsolatedCommProtoType.MSG_KEY_SUBFUNCTION + IsolatedCommProtoType.MSG_KEY_VALUE_CONNECTOR + this.subFunction);
		}
		String msgParasTmp = this.msgParas;
		if(!StringUtils.isBlank(msgParasTmp)) {
//			sBuffer.append(IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
			//{"HardName":"FT","HardVersion":"1.0.1","CPU":"Intel Core i7","RAM":"16G","SysVersion":"1.0.1","SoftWare":"1.0.1"}
			msgParasTmp = msgParasTmp.replace("{\"", IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
			msgParasTmp = msgParasTmp.replace("\":\"", IsolatedCommProtoType.MSG_KEY_VALUE_CONNECTOR);
			msgParasTmp = msgParasTmp.replace("\",\"", IsolatedCommProtoType.MSG_FIELD_CONNECTOR);
			msgParasTmp = msgParasTmp.replace("\"}", "");
			sBuffer.append(msgParasTmp);
		}
		
		return sBuffer.toString();	
	}
   
}
