package com.mf.config;

import android.util.Log;

import com.mf.config.domain.ConfigEncryptEnum;
import com.mf.config.domain.ConfigParam;
import com.mf.config.domain.ConfigTypeEnum;
import com.mf.config.utils.AES256Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GlobalConfig extends Locker implements IGlobalConfig {
    private static final String TAG = GlobalConfig.class.getSimpleName();

    private static String CLOUD_CONFIG_FILE = FileIOUtils.MF_CONFIG + "/mf-cloud-config.dat";
    private static String LOCAL_CONFIG_FILE = FileIOUtils.MF_CONFIG + "/mf-local-config.dat";

    /**
     * 配置文件路径，以及该文件中的配置信息
     */
    private final Map<String, Map<String, Object>> fileConfigsMap = new HashMap<>();

    /**
     * 所有的配置信息
     */
    private final Map<String, Object> allConfigsMap = new HashMap<>();

    /**
     * 参数
     */
    private List<ConfigParam> configParams;
    /**
     * 参数与文件名映射map
     */
    private Map<String, ConfigParam> configParamMap;

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    private static class SingletonHolder {
        private static final GlobalConfig INSTANCE = new GlobalConfig();
    }

    public static GlobalConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private GlobalConfig() {

    }

    private static String DEFAULT_ENCRYPT_KEY = "bbtvx!tFfOde5nP8tSb1AfMKwE!yxx^B";

    public static List<ConfigParam> getDefaultNoEncryptConfigParam() {
        List<ConfigParam> configParams = new ArrayList<>();
        configParams.add(new ConfigParam(CLOUD_CONFIG_FILE, ConfigTypeEnum.CLOUD_CONFIG, ConfigEncryptEnum.NO_ENCRYPT, null));
        configParams.add(new ConfigParam(LOCAL_CONFIG_FILE, ConfigTypeEnum.LOCAL_CONFIG, ConfigEncryptEnum.NO_ENCRYPT, null));
        return configParams;
    }

    public static List<ConfigParam> getDefaultEncryptConfigParam() {
        List<ConfigParam> configParams = new ArrayList<>();
        configParams.add(new ConfigParam(CLOUD_CONFIG_FILE, ConfigTypeEnum.CLOUD_CONFIG, ConfigEncryptEnum.ENCRYPT_ALL, DEFAULT_ENCRYPT_KEY));
        configParams.add(new ConfigParam(LOCAL_CONFIG_FILE, ConfigTypeEnum.LOCAL_CONFIG, ConfigEncryptEnum.ENCRYPT_ALL, DEFAULT_ENCRYPT_KEY));
        return configParams;
    }

    @Override
    public String getCloudConfigFile() {
        return CLOUD_CONFIG_FILE;
    }

    @Override
    public String getLocalConfigFile() {
        return LOCAL_CONFIG_FILE;
    }

    @Override
    public boolean init(List<ConfigParam> params) {
        if (isInit.getAndSet(true)) {
            Log.w(TAG, "已经初始化过了");
            return false;
        }
        if (params == null || params.isEmpty()) {
            this.configParams = getDefaultNoEncryptConfigParam();
        } else {
            this.configParams = params;
        }

        for (ConfigParam configParam : this.configParams) {
            if (configParam.getConfigTypeEnum() == ConfigTypeEnum.CLOUD_CONFIG) {
                CLOUD_CONFIG_FILE = configParam.getFilename();
            }
            if (configParam.getConfigTypeEnum() == ConfigTypeEnum.LOCAL_CONFIG) {
                LOCAL_CONFIG_FILE = configParam.getFilename();
            }
        }
        configParamMap = this.configParams.stream().collect(Collectors.toMap(ConfigParam::getFilename,
                Function.identity(), (configParam, configParam2) -> {
                    Log.w(TAG, "相同的配置 configParam:" + configParam +
                            " configParam2:" + configParam2);
                    return configParam2;
                }));
        reloadConfig(true);
        return true;
    }

    @Override
    public void reloadConfig(boolean clearLoadedConfig) {
        checkIsInit();
        try {
            lockRead();
            TimeCosts costs = new TimeCosts();

            if (clearLoadedConfig) {
                fileConfigsMap.clear();
                allConfigsMap.clear();
            }

            for (ConfigParam configParam : configParams) {
                String filename = configParam.getFilename();
                if (!FileIOUtils.hasFile(filename)) {
                    continue;
                }
                String data = FileIOUtils.readFile(filename);
                if (data.length() > 0) {
                    if (!data.startsWith("{") && configParam.getConfigEncryptEnum() == ConfigEncryptEnum.ENCRYPT_ALL) {
                        String encryptKey = configParam.getEncryptKey();
                        if (encryptKey == null || encryptKey.length() == 0) {
                            encryptKey = DEFAULT_ENCRYPT_KEY;
                        }
                        data = AES256Util.decode(encryptKey, data);
                    }
                    Map<String, Object> map = new HashMap<>();
                    JsonConfigLoader.getInstance().load(data, map);
                    fileConfigsMap.put(filename, map);
                    allConfigsMap.putAll(map);
                }
            }

            Log.v(TAG, String.format("load configs: size=%d, cost:%fms",
                    allConfigsMap.size(), (costs.end() / 1000.0f)));
        } finally {
            unlockRead();
        }
    }

    @Override
    public GlobalConfig putString(String key, String value) {
        putObject(CLOUD_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putInt(String key, int value) {
        putObject(CLOUD_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putLong(String key, long value) {
        putObject(CLOUD_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putDouble(String key, double value) {
        putObject(CLOUD_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putBoolean(String key, boolean value) {
        putObject(CLOUD_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putStringLocal(String key, String value) {
        putObject(LOCAL_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putIntLocal(String key, int value) {
        putObject(LOCAL_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putLongLocal(String key, long value) {
        putObject(LOCAL_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putDoubleLocal(String key, double value) {
        putObject(LOCAL_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putBooleanLocal(String key, boolean value) {
        putObject(LOCAL_CONFIG_FILE, key, value);
        return this;
    }

    @Override
    public GlobalConfig putString(String file, String key, String value) {
        putObject(file, key, value);
        return this;
    }

    @Override
    public GlobalConfig putInt(String file, String key, int value) {
        putObject(file, key, value);
        return this;
    }

    @Override
    public GlobalConfig putLong(String file, String key, long value) {
        putObject(file, key, value);
        return this;
    }

    @Override
    public GlobalConfig putDouble(String file, String key, double value) {
        putObject(file, key, value);
        return this;
    }

    @Override
    public GlobalConfig putBoolean(String file, String key, boolean value) {
        putObject(file, key, value);
        return this;
    }

    @Override
    public String getString(String key, String defaultValue) {
        checkIsInit();
        Object obj = allConfigsMap.get(key);
        String value = ObjectParse.toString(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        checkIsInit();
        Object obj = allConfigsMap.get(key);
        Integer value = ObjectParse.toInteger(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        checkIsInit();
        Object obj = allConfigsMap.get(key);
        Double value = ObjectParse.toDouble(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        checkIsInit();
        Object obj = allConfigsMap.get(key);
        Long value = ObjectParse.toLong(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        checkIsInit();
        Object obj = allConfigsMap.get(key);
        Boolean value = ObjectParse.toBoolean(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public Map<String, Object> getMap() {
        checkIsInit();
        return allConfigsMap;
    }

    @Override
    public boolean contains(String key) {
        checkIsInit();
        return allConfigsMap.containsKey(key);
    }

    @Override
    public void save() {
        try {
            lockWrite();
            save(CLOUD_CONFIG_FILE, fileConfigsMap.getOrDefault(CLOUD_CONFIG_FILE, new HashMap<>()));
        } finally {
            unlockWrite();
        }
    }

    @Override
    public void saveLocal() {
        try {
            lockWrite();
            save(LOCAL_CONFIG_FILE, fileConfigsMap.getOrDefault(LOCAL_CONFIG_FILE, new HashMap<>()));
        } finally {
            unlockWrite();
        }
    }

    @Override
    public void save(String file) {
        try {
            lockWrite();
            save(file, fileConfigsMap.getOrDefault(file, new HashMap<>()));
        } finally {
            unlockWrite();
        }
    }

    private void putObject(String file, String key, Object value) {
        checkIsInit();
        Map<String, Object> map = fileConfigsMap.computeIfAbsent(file,
                s -> new HashMap<>());
        if (value == null) {
            map.remove(key);
            allConfigsMap.remove(key);
            return;
        }
        if (value instanceof Number) {
            // deviate from the original by checking all Numbers, not just floats & doubles
            ObjectParse.checkDouble(((Number) value).doubleValue());
        }
        map.put(key, value);
        allConfigsMap.put(key, value);
    }

    private void save(String filename, Map<String, Object> map) {
        checkIsInit();
        if (!configParamMap.containsKey(filename)) {
            throw new RuntimeException(String.format("非法文件:%s，该文件不包含在初始化参数的文件名列表中", filename));
        }
        FileIOUtils.checkFile(filename);
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                Log.w(TAG, String.format("put key:%s value:%s failed", entry.getKey(), entry.getValue().toString()), e);
            }
        }
        try {
            String json = jsonObject.toString(4);
            ConfigParam configParam = configParamMap.get(filename);
            if (configParam != null && configParam.getConfigEncryptEnum() == ConfigEncryptEnum.ENCRYPT_ALL) {
                String encryptKey = configParam.getEncryptKey();
                if (encryptKey == null || encryptKey.length() == 0) {
                    encryptKey = DEFAULT_ENCRYPT_KEY;
                }
                json = AES256Util.encode(encryptKey, json);
            }
            FileIOUtils.write(filename, json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIsInit() {
        if (!isInit.get()) {
            throw new RuntimeException("GlobalConfig 未初始化");
        }
    }
}
