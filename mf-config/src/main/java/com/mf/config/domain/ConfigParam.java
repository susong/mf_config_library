package com.mf.config.domain;

public class ConfigParam {

    /**
     * 配置文件名
     */
    private String filename;

    /**
     * 配置类型
     * 现在默认分为云端配置和本地配置，两个配置的key相同时，本地配置会覆盖云端配置，
     * 多个对象的 configTypeEnum 值为 {ConfigTypeEnum.CLOUD_CONFIG 云端配置} 时，只生效最后一个，其他的当 {ConfigTypeEnum.}OTHER_CONFIG 其他配置} 处理
     * 多个对象的 configTypeEnum 值为 {ConfigTypeEnum.LOCAL_CONFIG 本地配置} 时，只生效最后一个，其他的当 {ConfigTypeEnum.}OTHER_CONFIG 其他配置} 处理
     * {ConfigTypeEnum.}OTHER_CONFIG 其他配置} 可以有多个。
     *
     */
    private ConfigTypeEnum configTypeEnum;

    /**
     * 加密枚举
     */
    private ConfigEncryptEnum configEncryptEnum = ConfigEncryptEnum.NO_ENCRYPT;

    /**
     * 加密秘钥，推荐长度32位
     */
    private String encryptKey;

    public ConfigParam() {
    }

    public ConfigParam(String filename, ConfigTypeEnum configTypeEnum) {
        this.filename = filename;
        this.configTypeEnum = configTypeEnum;
    }

    public ConfigParam(String filename, ConfigTypeEnum configTypeEnum, ConfigEncryptEnum configEncryptEnum, String encryptKey) {
        this.filename = filename;
        this.configTypeEnum = configTypeEnum;
        this.configEncryptEnum = configEncryptEnum;
        this.encryptKey = encryptKey;
    }

    public String getFilename() {
        return filename;
    }

    public ConfigParam setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public ConfigTypeEnum getConfigTypeEnum() {
        return configTypeEnum;
    }

    public ConfigParam setConfigTypeEnum(ConfigTypeEnum configTypeEnum) {
        this.configTypeEnum = configTypeEnum;
        return this;
    }

    public ConfigEncryptEnum getConfigEncryptEnum() {
        return configEncryptEnum;
    }

    public ConfigParam setConfigEncryptEnum(ConfigEncryptEnum configEncryptEnum) {
        this.configEncryptEnum = configEncryptEnum;
        return this;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public ConfigParam setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
        return this;
    }

    @Override
    public String toString() {
        return "ConfigParam{" +
                "filename='" + filename + '\'' +
                ", configTypeEnum=" + configTypeEnum +
                ", configEncryptEnum=" + configEncryptEnum +
                ", encryptKey='" + encryptKey + '\'' +
                '}';
    }
}
