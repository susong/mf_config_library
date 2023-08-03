package com.mf.config;

import com.mf.config.domain.ConfigParam;

import java.util.List;
import java.util.Map;

public interface IGlobalConfig {

    /**
     * 获取云端配置文件
     *
     * @return
     */
    String getCloudConfigFile();

    /**
     * 获取本地配置文件
     *
     * @return
     */
    String getLocalConfigFile();

    /**
     * 初始化
     * 使用不加密的默认设置
     *
     * @return
     */
    boolean init();

    /**
     * 初始化
     * 传入加密秘钥，其他都使用加密的默认设置
     *
     * @param encryptKey 加密秘钥
     * @return
     */
    boolean init(String encryptKey);

    /**
     * 初始化
     *
     * @param configParams 配置参数，
     *                     现在默认分为云端配置和本地配置，
     *                     两个配置的key相同时，本地配置会覆盖云端配置，
     *                     可自定义ConfigTypeEnum
     * @return
     */
    boolean init(List<ConfigParam> configParams);

    void reloadConfig(boolean clearLoadedConfig);

    IGlobalConfig putString(String key, String value);

    IGlobalConfig putInt(String key, int value);

    IGlobalConfig putLong(String key, long value);

    IGlobalConfig putDouble(String key, double value);

    IGlobalConfig putBoolean(String key, boolean value);

    IGlobalConfig putMap(Map<String, Object> map);

    IGlobalConfig putStringCloud(String key, String value);

    IGlobalConfig putIntCloud(String key, int value);

    IGlobalConfig putLongCloud(String key, long value);

    IGlobalConfig putDoubleCloud(String key, double value);

    IGlobalConfig putBooleanCloud(String key, boolean value);

    IGlobalConfig putMapCloud(Map<String, Object> map);

    IGlobalConfig putString(String filename, String key, String value);

    IGlobalConfig putInt(String filename, String key, int value);

    IGlobalConfig putLong(String filename, String key, long value);

    IGlobalConfig putDouble(String filename, String key, double value);

    IGlobalConfig putBoolean(String filename, String key, boolean value);

    IGlobalConfig putMap(String filename, Map<String, Object> map);

    String getString(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    double getDouble(String key, double defaultValue);

    long getLong(String key, long defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    Map<String, Object> getMap();

    Map<String, Object> getMapCloud();

    Map<String, Object> getMap(String filename);

    Map<String, Object> getAllMap();

    boolean contains(String key);

    void save();

    void saveCloud();

    void save(String filename);
}
