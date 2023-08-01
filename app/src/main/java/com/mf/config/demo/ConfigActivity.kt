package com.mf.face.demo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mf.config.R
import com.permissionx.guolindev.PermissionX

class ConfigActivity : FragmentActivity(), View.OnClickListener {
    private val TAG: String = ConfigActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        initView()
        requestPermissions()
    }

    private fun initView() {
        findViewById<Button>(R.id.btn_go_home).setOnClickListener(this)
        findViewById<Button>(R.id.btn_go_setting).setOnClickListener(this)
    }

    private fun requestPermissions() {
        val permissionList = mutableListOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        PermissionX.init(this)
            .permissions(permissionList)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                if (beforeRequest) {
                    scope.showRequestReasonDialog(
                        deniedList,
                        "即将申请的权限是程序必须依赖的权限", "我已明白"
                    )
                } else {
                    scope.showRequestReasonDialog(
                        deniedList,
                        "即将重新申请的权限是程序必须依赖的权限", "我已明白"
                    )
                }
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "您需要去应用程序设置当中手动开启权限", "去设置"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                LogUtils.d(
                    "allGranted:$allGranted grantedList:$grantedList " +
                            "deniedList:$deniedList"
                )
                if (allGranted) {
//                    ToastUtils.showShort("所有申请的权限都已通过")
                    onAllPermissionsGranted()
                } else {
//                    ToastUtils.showLong("您拒绝了如下权限：$deniedList")
                }
            }
    }

    private fun onAllPermissionsGranted() {
        SPUtils.getInstance().put("isFirstLaunch", false)
    }

    private fun goSystemSettingPage(context: Context) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun goMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        // 跳转无动画
        overridePendingTransition(0, 0)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_go_home -> {
                goMainActivity()
            }

            R.id.btn_go_setting -> {
                goSystemSettingPage(this)
            }
        }
    }
}

