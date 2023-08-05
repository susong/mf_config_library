package com.mf.face.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.mf.config.GlobalConfig
import com.mf.config.R

class MainActivity : AppCompatActivity() {

    val TAG = "mf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn_init).setOnClickListener {
            val result =
                GlobalConfig.getInstance().init(GlobalConfig.getDefaultNoEncryptConfigParam())
            Log.d(TAG, "调用初始化的结果：" + result);
        }
        findViewById<Button>(R.id.btn_reload).setOnClickListener {
            try {
                GlobalConfig.getInstance().reloadConfig(true)
            } catch (e: Exception) {
                Log.e(TAG, "重新加载失败", e);
            }
        }
        findViewById<Button>(R.id.btn_write_cloud_config).setOnClickListener {
            try {
                GlobalConfig.getInstance()
                    .putStringCloud("key_string", "this is local config")
                    .putIntCloud("key_int", 101)
                    .putLongCloud("key_long", 8888888888L)
                    .putDoubleCloud("key_double", 6.18)
                    .putBooleanCloud("key_boolean", true)
                GlobalConfig.getInstance().saveCloud()

            } catch (e: Exception) {
                Log.e(TAG, "保存配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_write_local_config).setOnClickListener {
            try {
                GlobalConfig.getInstance()
                    .putString("key_string", "this is cloud config")
                    .putInt("key_int", 100)
                    .putLong("key_long", 99999999999999L)
                    .putDouble("key_double", 3.1415926)
                    .putBoolean("key_boolean", true)
                GlobalConfig.getInstance().save()
            } catch (e: Exception) {
                Log.e(TAG, "保存配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_read_config).setOnClickListener {
            try {
                val strValue =
                    GlobalConfig.getInstance().getString("key_string", "default string")
                val intValue = GlobalConfig.getInstance().getInt("key_int", 0)
                val longValue = GlobalConfig.getInstance().getLong("key_long", 0)
                val doubleValue = GlobalConfig.getInstance().getDouble("key_double", 0.0)
                val booleanValue = GlobalConfig.getInstance().getBoolean("key_boolean", false)
                val value =
                    GlobalConfig.getInstance().getString("key_string2", "default local string")

                Log.d(TAG, "" + strValue)
                Log.d(TAG, "" + intValue)
                Log.d(TAG, "" + longValue)
                Log.d(TAG, "" + doubleValue)
                Log.d(TAG, "" + booleanValue)
                Log.d(TAG, "" + value)
                Log.d(TAG, "map: " + JSON.toJSONString(GlobalConfig.getInstance().map))
                Log.d(TAG, "mapCloud: " + JSON.toJSONString(GlobalConfig.getInstance().mapCloud))
                Log.d(TAG, "allMap: " + JSON.toJSONString(GlobalConfig.getInstance().allMap))
            } catch (e: Exception) {
                Log.e(TAG, "读取配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_write_map).setOnClickListener {
            try {
                val json = """
                    {
                        "robot.character.clothes.dance": "09000004,09001007,09012027",
                        "robot.background.texture": "Background/background_future_city.png",
                        "garage.id": "5678",
                        "timerInterval": "10000",
                        "robot.character.lipsync": "09000004",
                        "specialConfig": [],
                        "installer-config": {
                            "parking.id": "1234",
                            "garage.id": "5678",
                            "device.id": "1357",
                            "sn": "07011923030005",
                            "deviceType": 1,
                            "enableTimer": true,
                            "timerInterval": 1800000,
                            "serverConfigs": [
                                {
                                    "serverHost": "https://xxxxxx.cn",
                                    "apkQueryUrl": "/management/v1/semi-public/web-api/device-version/version",
                                    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoX2xvZ2luIiwiaXNzIjoiTUZfU1pfMjIiLCJleHAiOjE5OTU0NTU0NzAsImlhdCI6MTY4MDA5NTQ3MCwidXNlcklkIjoiMTYzOTE1MjgxNDAwMDQwNjUzMCIsImp0aSI6IjM4MWVjMmY0LTA2ZGItNDkyZi1hNjU4LWM1OTFjZmI1ZTIzNSJ9.mahzs8ohJ70O28Hd1nQ1wEFBadDElBbrz9XNvNjCKlQ"
                                }
                            ],
                            "apkConfigs": [
                                {
                                    "packageName": "com.xxx.xxx.terminal"
                                }
                            ]
                        },
                        "garageId": 5678,
                        "device.id": "1357",
                        "deviceId": 1357,
                        "gateName": "1库门",
                        "mac": "2a:cc:37:d0:9f:dd",
                        "password": "666888",
                        "mec.websocket.server.host": "ws://xxxxxx:8080",
                        "mec.http.server.host": "http://xxxxxx:8080",
                        "keepAliveActivity": {
                            "packageName": "com.xxx.xxx.terminal",
                            "componentName": "com.xxx.xxx.terminal.MainActivity"
                        },
                        "baseConfig": {
                            "volume": "70",
                            "brightness": "100"
                        },
                        "deviceType": 1,
                        "robot.character.clothes": "xxxx",
                        "garageName": "码飞升降横移车库A库",
                        "parkingId": 1234,
                        "parking.id": "1234",
                        "cloud.http.server.host": "http://xxxxxx.cn",
                        "enableTimer": "false",
                        "global-config": {
                            "mec.http.server.host": "http://xxxxxx:8080",
                            "mec.websocket.server.host": "ws://xxxxxx:8080",
                            "parking.id": "1234",
                            "garage.id": "5678",
                            "device.id": "1357"
                        },
                        "resource-config": [
                            {
                                "url": "https://xxxxxxxxxxxx.cn/xxx-application-package/2023-04-10/1681103256607/com.xxx.xxx.terminal_v0.0.11.apk",
                                "md5": "33fa9d50824990a0e9eff175578674f3",
                                "targetPath": "/sdcard/ter.apk",
                                "isDecompression": false,
                                "isNeedInstall": true,
                                "package": "com.xxx.xxx.installer"
                            },
                            {
                                "url": "https://xxxxxxxxxxxx.cn/device_screen/2023-02-08/segment-anything.zip",
                                "md5": "8ca1bb1d4cdea56b394c58372adb797a",
                                "targetPath": "/sdcard/xxx/config/s.zip",
                                "isDecompression": true,
                                "isNeedInstall": false
                            }
                        ],
                        "robot.tip.state": "false",
                        "keepAliveService": [
                            {
                                "packageName": "com.xxx.xxx.installer",
                                "componentName": "com.xxx.xxx.installer.service.InstallerService"
                            }
                        ],
                        "baidu.face.serialnumber": "xxxx-xxxx-xxxx-xxxx",
                        "bgUrl": "https://xxxxxxxxxxxx.cn/device_screen/2023-02-04/bg.png",
                        "robot.tip.home.animation": "false"
                    }
                """.trimIndent();
                val jsonObject = JSON.parseObject(json)
                GlobalConfig.getInstance().putMap(jsonObject, false)
                GlobalConfig.getInstance().save()
            } catch (e: Exception) {
                Log.e(TAG, "保存配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_write_map_and_replace).setOnClickListener {
            try {
                val json = """
                    {
                        "garageId": 5678,
                        "deviceId": 1357,
                        "gateName": "1库门",
                        "aaaa":"bbbb"
                    }
                """.trimIndent();
                val jsonObject = JSON.parseObject(json)
                GlobalConfig.getInstance().putMap(jsonObject, true)
                GlobalConfig.getInstance().save()
            } catch (e: Exception) {
                Log.e(TAG, "保存配置失败", e);
            }
        }
    }
}