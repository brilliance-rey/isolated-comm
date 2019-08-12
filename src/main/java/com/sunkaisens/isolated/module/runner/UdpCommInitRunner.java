package com.sunkaisens.isolated.module.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.sunkaisens.isolated.module.service.UdpNetService;

import lombok.extern.slf4j.Slf4j;

/**
 * UDP通信初始化
 * @author RenEryan
 */
@Slf4j
@Component
public class UdpCommInitRunner implements ApplicationRunner {

    @Autowired
    private UdpNetService udpNetService;

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        	
		if (!udpNetService.initUdpComm()) {
			log.error(" ____   __    _   _ ");
			log.error("| |_   / /\\  | | | |");
			log.error("|_|   /_/--\\ |_| |_|__");
			log.error("                        ");
			log.error("UPD服务启动失败！");
			// 关闭 系统
			context.close();
		} else {
			log.info("UPD服务启动成功！");
		}
    }
}
