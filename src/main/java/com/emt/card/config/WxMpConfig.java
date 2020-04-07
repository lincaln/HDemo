package com.emt.card.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfig {

    private WxMpProperties wxMpProperties;

    @Autowired
    public WxMpConfig(WxMpProperties properties) {
        this.wxMpProperties = properties;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(wxMpProperties.getAppId());
        configStorage.setSecret(wxMpProperties.getSecret());

        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }
    
    @Bean
    public WxMpService wxPcService() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(wxMpProperties.getPcAppId());
        configStorage.setSecret(wxMpProperties.getPcSecret());

        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }
}
