package com.mf.face.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mf.config.GlobalConfig
import com.mf.config.R

class MainActivity : AppCompatActivity() {

    val TAG = "mf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn_init).setOnClickListener {
            val result =
                GlobalConfig.getInstance().init(GlobalConfig.getDefaultEncryptConfigParam())
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
        findViewById<Button>(R.id.btn_read_cloud_config).setOnClickListener {
            try {
                val strValue = GlobalConfig.getInstance().getString("key_string", "default string")
                val intValue = GlobalConfig.getInstance().getInt("key_int", 0)
                val longValue = GlobalConfig.getInstance().getLong("key_long", 0)
                val doubleValue = GlobalConfig.getInstance().getDouble("key_double", 0.0)
                val booleanValue = GlobalConfig.getInstance().getBoolean("key_boolean", false)
                val value =
                    GlobalConfig.getInstance().getString("key_string2", "default cloud string")

                Log.d(TAG, "" + strValue)
                Log.d(TAG, "" + intValue)
                Log.d(TAG, "" + longValue)
                Log.d(TAG, "" + doubleValue)
                Log.d(TAG, "" + booleanValue)
                Log.d(TAG, "" + value)
            } catch (e: Exception) {
                Log.e(TAG, "读取配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_write_local_config).setOnClickListener {
            try {
                GlobalConfig.getInstance()
                    .putStringLocal("key_string", "this is local config")
                    .putIntLocal("key_int", 101)
                    .putLongLocal("key_long", 8888888888L)
                    .putDoubleLocal("key_double", 6.18)
                    .putBooleanLocal("key_boolean", true)
                GlobalConfig.getInstance().saveLocal()

            } catch (e: Exception) {
                Log.e(TAG, "保存配置失败", e);
            }
        }
        findViewById<Button>(R.id.btn_read_local_config).setOnClickListener {
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
            } catch (e: Exception) {
                Log.e(TAG, "读取配置失败", e);
            }
        }
    }
}