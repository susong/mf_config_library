package com.mf.config;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * json配置文件加载器
 */
class JsonConfigLoader {
    private static final String TAG = JsonConfigLoader.class.getSimpleName();

    private static class SingletonHolder {
        private static JsonConfigLoader INSTANCE = new JsonConfigLoader();
    }

    static JsonConfigLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private JsonConfigLoader() {
    }

    void load(String json, Map<String, Object> map) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            map.putAll(toMap(jsonObject));
        } catch (JSONException e) {
            Log.w(TAG, "解析json文件异常", e);
        }
    }

    /**
     * 将 JSONObject 转换为 Map<String, Object>
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysIterator = jsonObject.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                // 递归处理嵌套的 JSON 对象
                value = toMap((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // 将 JSONArray 转换为 List<Object>
                value = toList((JSONArray) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * 将 JSONArray 转换为 List<Object>
     *
     * @param array
     * @return
     * @throws JSONException
     */
    private List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONObject) {
                // 递归处理嵌套的 JSON 对象
                value = toMap((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // 将嵌套的 JSONArray 转换为 List<Object>
                value = toList((JSONArray) value);
            }
            list.add(value);
        }
        return list;
    }
}
