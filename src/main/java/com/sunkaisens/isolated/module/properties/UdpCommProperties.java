package com.sunkaisens.isolated.module.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 
 * Description: UDP通信属性
 * 
 * @author RenEryan
 * @since 2019年8月8日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "isolated.udp.comm")
public class UdpCommProperties {

	private String localIp;
	
	private String localPort;

	private String remoteIp;
	
	private String remotePort;

}
