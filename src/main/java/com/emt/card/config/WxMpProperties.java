package com.emt.card.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    private String appId;

    private String secret;
    
    private String pcAppId;

    private String pcSecret;
}
