package com.yifeplayte.magicpointerswitcher

import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.TextSummaryV
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainActivity : MIUIActivity() {
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    init {
        initView {
            registerMain(getString(R.string.app_name), false) {
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
                            } catch (e: Exception) {
                                showToast("尝试使用原生鼠标指针失败")
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
                            } catch (e: Exception) {
                                showToast("尝试使用Magic Pointer失败")
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
                            } catch (e: Exception) {}
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
