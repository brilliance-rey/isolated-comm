package com.sunkaisens.isolated.module.constant;


/**   
 * @ClassName:  IsolatedCommProtoType   
 * @Description: 隔离卡通信私有协议操作类型定义
 * @author: RenEryan 
 * @date:   2018年12月13日 下午12:37:12   
 *     
 * @Copyright: www.sunkaisens.com Inc. All rights reserved. 
 */
public class IsolatedCommProtoType {

	/** 消息字符集 */
	public static final String MSG_CHARSET_NAME = "UTF-8";
	
	/** 消息字段连接符 */
	public static final String MSG_FIELD_CONNECTOR = "/";
	/** 消息key/value连接符 */
	public static final String MSG_KEY_VALUE_CONNECTOR = ":";
	
	/** 消息流向 */
	public static final String MSG_KEY_MACCUIMSG = "MAccUIMsg";
	/** 通信板卡 */
	public static final String MSG_KEY_MODULE = "Module";
	/** 通信功能 */
	public static final String MSG_KEY_FUNCTION = "Function";
	/** 通信子功能 */
	public static final String MSG_KEY_SUBFUNCTION = "SubFunction";
	
	/** 消息流向: UI到System */
	public static final String MACCUIMSG_UI_TO_SYS = "UIToSys";
	/** 消息流向: System到UI */
	public static final String MACCUIMSG_SYS_TO_UI = "SysToUI";

	/** FT主机 */
	public static final String MODULE_FT = "FT";
	/** 隔离卡 */
	public static final String MODULE_GL = "GL";
	/** 主通信卡 */
	public static final String MODULE_MASTER = "Master";
	/** 从通信卡 */
	public static final String MODULE_SLAVE = "Slave";
	
	/** 状态显示 */
	public static final String FUNCTION_STATUS = "Status";
	/** 系统配置 */
	public static final String FUNCTION_SYSCONFIG = "SysConfig";
	/** 专用服务 */
	public static final String FUNCTION_SERVICE = "Service";
	/** 日志 */
	public static final String FUNCTION_LOG = "Log";
	
	/** 硬件状态 */
	public static final String SUB_FUNCTION_HARDSTATUS = "HardStatus";
	/** 链路状态 */
	public static final String SUB_FUNCTION_LINKSTATUS = "linkStatus";
	
	/** 网络配置 */
	public static final String SUB_FUNCTION_NETCONFIG = "NetConfig";
	/** 联系人配置 */
	public static final String SUB_FUNCTION_CONTACTCONFIG = "ContactConfig";
	
	/** 链路测试 */
	public static final String SUB_FUNCTION_LINKTEST = "NetConfig";
	/** 业务测试*/
	public static final String SUB_FUNCTION_SERVICETEST = "ContactConfig";
	
}
