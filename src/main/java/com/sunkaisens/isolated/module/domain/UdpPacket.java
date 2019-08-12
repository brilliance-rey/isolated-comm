package com.sunkaisens.isolated.module.domain;

import lombok.Data;

@Data
public class UdpPacket {
	private String ipAddress;
	private int port;
}
