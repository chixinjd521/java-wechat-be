package com.dadiyang.wx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 配置项
 *
 * @author dadiyang
 * @date 2018/7/13
 */
@Configuration
public class AppConfig {
    @Value("${cryptRule}")
    private String cryptRule;
    @Value("${authCookieName}")
    private String authCookieName;
    @Value("${openwxServer}")
    private String openwxServer;
    @Value("${allowOrigins:}")
    private String allowOrigins;
    @Value("${cdnPath:}")
    private String cdnPath;
    @Value("${debugUser:}")
    private String debugUser;
    @Value("${loginUrl}")
    private String loginUrl;
    @Value("${psSignKeyPre}")
    private String psSignKeyPre;

    public AppConfig() {
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getCryptRule() {
        return cryptRule;
    }

    public void setCryptRule(String cryptRule) {
        this.cryptRule = cryptRule;
    }

    public String getAuthCookieName() {
        return authCookieName;
    }

    public void setAuthCookieName(String authCookieName) {
        this.authCookieName = authCookieName;
    }

    public String getOpenwxServer() {
        return openwxServer;
    }

    public void setOpenwxServer(String openwxServer) {
        this.openwxServer = openwxServer;
    }

    public String getAllowOrigins() {
        return allowOrigins;
    }

    public void setAllowOrigins(String allowOrigins) {
        this.allowOrigins = allowOrigins;
    }

    public String getCdnPath() {
        return cdnPath;
    }

    public void setCdnPath(String cdnPath) {
        this.cdnPath = cdnPath;
    }

    public String getDebugUser() {
        return debugUser;
    }

    public void setDebugUser(String debugUser) {
        this.debugUser = debugUser;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getPsSignKeyPre() {
        return psSignKeyPre;
    }

    public void setPsSignKeyPre(String psSignKeyPre) {
        this.psSignKeyPre = psSignKeyPre;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "cryptRule='" + cryptRule + '\'' +
                ", authCookieName='" + authCookieName + '\'' +
                ", openwxServer='" + openwxServer + '\'' +
                ", allowOrigins='" + allowOrigins + '\'' +
                ", cdnPath='" + cdnPath + '\'' +
                ", debugUser='" + debugUser + '\'' +
                ", loginUrl='" + loginUrl + '\'' +
                ", psSignKeyPre='" + psSignKeyPre + '\'' +
                '}';
    }
}
