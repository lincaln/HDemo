package com.emt.card.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {

    private String appId;

    private String secret;

    private String token;

    private String aesKey;

    private String msgDataFormat;
    
    private String mchId;
    
    private String mchKey;

    private String orderPayTempleId;

    private String orderSubmitTempleId;

    private String orderCancelTempleId;

    private String refundSuccessTempleId;
    
    private String pemUrl;
}
