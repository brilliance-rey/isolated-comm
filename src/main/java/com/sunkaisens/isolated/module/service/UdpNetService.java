package com.sunkaisens.isolated.module.service;

import javax.validation.Valid;

import com.sunkaisens.isolated.module.domain.ModuleParas;

/**
 * 
 * Description:UDP服务
 * 
 * @author RenEryan
 * @since 2019年8月8日
 */
public interface UdpNetService {

	/**
	 * 启动服务
	 * @return
	 * @return boolean
	 */
   boolean initUdpComm();
   
   /**
    * 下发配置消息
    * @param moduleParas
    * @return void
    */
   public void sendMsg(@Valid ModuleParas moduleParas);
   
}
