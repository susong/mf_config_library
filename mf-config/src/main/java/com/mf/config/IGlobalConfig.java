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

    IGlobalConfig putStringLocal(String key, String value);

    IGlobalConfig putIntLocal(String key, int value);

    IGlobalConfig putLongLocal(String key, long value);

    IGlobalConfig putDoubleLocal(String key, double value);

    IGlobalConfig putBooleanLocal(String key, boolean value);

    IGlobalConfig putString(String file, String key, String value);

    IGlobalConfig putInt(String file, String key, int value);

    IGlobalConfig putLong(String file, String key, long value);

    IGlobalConfig putDouble(String file, String key, double value);

    IGlobalConfig putBoolean(String file, String key, boolean value);

    String getString(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    double getDouble(String key, double defaultValue);

    long getLong(String key, long defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    Map<String, Object> getMap();

    boolean contains(String key);

    void save();

    void saveLocal();

    void save(String file);
}
