package com.haiya.infrastructure.monitoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 告警配置属性类
 * 用于绑定alerting前缀的配置属性
 */
@Component
@ConfigurationProperties(prefix = "alerting")
public class AlertingProperties {
    
    private boolean enabled = true;
    private final Levels levels = new Levels();
    private final Channels channels = new Channels();
    
    public static class Levels {
        private final Level warning = new Level();
        private final Level critical = new Level();
        private final Level emergency = new Level();
        
        static {
            // 初始化默认值
            // warning默认值已经在Level类中设置
            // critical默认值已经在Level类中设置
            // emergency默认值已经在Level类中设置
        }
        
        public Level getWarning() {
            return warning;
        }
        
        public Level getCritical() {
            return critical;
        }
        
        public Level getEmergency() {
            return emergency;
        }
    }
    
    public static class Level {
        private List<String> channels;
        private int repeatInterval;
        
        public List<String> getChannels() {
            return channels;
        }
        
        public void setChannels(List<String> channels) {
            this.channels = channels;
        }
        
        public int getRepeatInterval() {
            return repeatInterval;
        }
        
        public void setRepeatInterval(int repeatInterval) {
            this.repeatInterval = repeatInterval;
        }
    }
    
    public static class Channels {
        private final Email email = new Email();
        private final Sms sms = new Sms();
        private final Phone phone = new Phone();
        
        public Email getEmail() {
            return email;
        }
        
        public Sms getSms() {
            return sms;
        }
        
        public Phone getPhone() {
            return phone;
        }
    }
    
    public static class Email {
        private boolean enabled = true;
        private final Smtp smtp = new Smtp();
        private List<String> recipients;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public Smtp getSmtp() {
            return smtp;
        }
        
        public List<String> getRecipients() {
            return recipients;
        }
        
        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }
    }
    
    public static class Smtp {
        private String host;
        private int port;
        private String username;
        private String password;
        
        public String getHost() {
            return host;
        }
        
        public void setHost(String host) {
            this.host = host;
        }
        
        public int getPort() {
            return port;
        }
        
        public void setPort(int port) {
            this.port = port;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    public static class Sms {
        private boolean enabled = true;
        private String provider;
        private String accessKeyId;
        private String accessKeySecret;
        private String signName;
        private String templateCode;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public String getProvider() {
            return provider;
        }
        
        public void setProvider(String provider) {
            this.provider = provider;
        }
        
        public String getAccessKeyId() {
            return accessKeyId;
        }
        
        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }
        
        public String getAccessKeySecret() {
            return accessKeySecret;
        }
        
        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }
        
        public String getSignName() {
            return signName;
        }
        
        public void setSignName(String signName) {
            this.signName = signName;
        }
        
        public String getTemplateCode() {
            return templateCode;
        }
        
        public void setTemplateCode(String templateCode) {
            this.templateCode = templateCode;
        }
    }
    
    public static class Phone {
        private boolean enabled = true;
        private String provider;
        private String accessKeyId;
        private String accessKeySecret;
        private String ttsCode;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public String getProvider() {
            return provider;
        }
        
        public void setProvider(String provider) {
            this.provider = provider;
        }
        
        public String getAccessKeyId() {
            return accessKeyId;
        }
        
        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }
        
        public String getAccessKeySecret() {
            return accessKeySecret;
        }
        
        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }
        
        public String getTtsCode() {
            return ttsCode;
        }
        
        public void setTtsCode(String ttsCode) {
            this.ttsCode = ttsCode;
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Levels getLevels() {
        return levels;
    }
    
    public Channels getChannels() {
        return channels;
    }
}