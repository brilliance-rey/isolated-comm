package com.sunkaisens.isolated.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sysbase")
public class SysBaseProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;

}
