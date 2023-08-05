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

    /**
     * 重新加载配置
     *
     * @param clearLoadedConfig 是否先清除内存中的配置
     */
    void reloadConfig(boolean clearLoadedConfig);

    IGlobalConfig putString(String key, String value);

    IGlobalConfig putInt(String key, int value);

    IGlobalConfig putLong(String key, long value);

    IGlobalConfig putDouble(String key, double value);

    IGlobalConfig putBoolean(String key, boolean value);

    /**
     * 放置map配置，用于批量操作配置
     *
     * @param map
     * @param isReplace 是否替换原有配置，true替换，map为空时，相当于删除；false不替换，在原配置基础追加
     * @return
     */
    IGlobalConfig putMap(Map<String, Object> map, boolean isReplace);

    IGlobalConfig putStringCloud(String key, String value);

    IGlobalConfig putIntCloud(String key, int value);

    IGlobalConfig putLongCloud(String key, long value);

    IGlobalConfig putDoubleCloud(String key, double value);

    IGlobalConfig putBooleanCloud(String key, boolean value);

    /**
     * 放置map配置，用于批量操作配置
     *
     * @param map       map配置
     * @param isReplace 是否替换原有配置，true替换，map为空时，相当于删除；false不替换，在原配置基础追加
     * @return
     */
    IGlobalConfig putMapCloud(Map<String, Object> map, boolean isReplace);

    IGlobalConfig putString(String filename, String key, String value);

    IGlobalConfig putInt(String filename, String key, int value);

    IGlobalConfig putLong(String filename, String key, long value);

    IGlobalConfig putDouble(String filename, String key, double value);

    IGlobalConfig putBoolean(String filename, String key, boolean value);

    /**
     * 放置map配置，用于批量操作配置
     *
     * @param filename  文件名
     * @param map       map配置
     * @param isReplace 是否替换原有配置，true替换，map为空时，相当于删除；false不替换，在原配置基础追加
     * @return
     */
    IGlobalConfig putMap(String filename, Map<String, Object> map, boolean isReplace);

    String getString(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    double getDouble(String key, double defaultValue);

    long getLong(String key, long defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    /**
     * 获取所有本地类型的配置
     *
     * @return
     */
    Map<String, Object> getMap();

    /**
     * 获取所有云端类型的配置
     *
     * @return
     */
    Map<String, Object> getMapCloud();

    /**
     * 获取指定文件的配置
     *
     * @param filename
     * @return
     */
    Map<String, Object> getMap(String filename);

    /**
     * 获取所有配置
     *
     * @return
     */
    Map<String, Object> getAllMap();

    boolean contains(String key);

    /**
     * 保存本地类型的配置
     */
    void save();

    /**
     * 保存云端类型的配置
     */
    void saveCloud();

    /**
     * 保存指定文件的配置
     *
     * @param filename
     */
    void save(String filename);
}
