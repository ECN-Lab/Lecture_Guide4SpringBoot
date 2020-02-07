package com.ntsphere.ecn.basicweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="server")
public class ServerProperty {
	private String company;
	private String channel;
	private Integer year;
}
