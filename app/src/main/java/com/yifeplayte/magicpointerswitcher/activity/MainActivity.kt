package com.yifeplayte.magicpointerswitcher.activity

import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.TextSummaryV
import com.yifeplayte.magicpointerswitcher.R
import com.yifeplayte.magicpointerswitcher.util.ExceptionUtil.getStackTraceInfo
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainActivity : MIUIActivity() {
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    init {
        initView {
            registerMain(getString(R.string.app_name), false) {
                Line()
                TitleText("这是一个为小米平板5系列提供免root权限切换鼠标样式的工具。")
                TitleText("此软件只可在小米平板5系列且较新版本的MIUI13上运行。")
                TitleText("您可以在控制中心里添加原生鼠标切换的快捷方式而不需要进入本软件。")
                TitleText("此页面将仅供测试除错。")
                TextSummaryArrow(
                    TextSummaryV(
                        text = "使用原生鼠标指针",
                        onClickListener = {
                            try {
                                val im = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    null,
                                    "getInstance"
                                )
                                HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    im,
                                    "setPointerVisibility",
                                    true
                                )
                                showToast("尝试使用原生鼠标指针成功")
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast("尝试使用原生鼠标指针失败：$exceptionString")
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        text = "使用Magic Pointer",
                        onClickListener = {
                            try {
                                val im = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    null,
                                    "getInstance"
                                )
                                HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    im,
                                    "setPointerVisibility",
                                    false
                                )
                                showToast("尝试使用Magic Pointer成功")
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast("尝试使用Magic Pointer失败：$exceptionString")
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        text = "获取当前指针状态",
                        onClickListener = {
                            try {
                                val im = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    null,
                                    "getInstance"
                                )
                                val state = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    im,
                                    "getPointerVisibility"
                                )
                                showToast("尝试获取当前指针状态成功，当前状态：$state")
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast("尝试获取当前指针状态失败：$exceptionString")
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        text = "切换指针样式",
                        onClickListener = {
                            try {
                                val im = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    null,
                                    "getInstance"
                                )
                                var state = HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    im,
                                    "getPointerVisibility"
                                )
                                state = state != true
                                HiddenApiBypass.invoke(
                                    InputManager::class.java,
                                    im,
                                    "setPointerVisibility",
                                    state
                                )
                                showToast("尝试切换指针样式成功")
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast("尝试切换指针样式失败：$exceptionString")
                            }
                        }
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setSP(getPreferences(0))
        super.onCreate(savedInstanceState)
    }

    private fun showToast(string: String) {
        handler.post {
            Toast.makeText(this, string, Toast.LENGTH_LONG).show()
        }
    }
}
