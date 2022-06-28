package com.yifeplayte.magicpointerswitcher.activity

import android.annotation.SuppressLint
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yifeplayte.magicpointerswitcher.R
import com.yifeplayte.magicpointerswitcher.util.ExceptionUtil.getStackTraceInfo
import com.yifeplayte.magicpointerswitcher.util.Utils
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainActivity : MIUIActivity() {
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            setSP(getPreferences(0))
        } else {
            checkLSPosed()
        }
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed() {
        try {
            setSP(getSharedPreferences("config", MODE_WORLD_READABLE))
        } catch (exception: SecurityException) {
            setSP(getPreferences(0))
            MIUIDialog(this) {
                setTitle(R.string.warning)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
                    dismiss()
                }
            }.show()
        }
    }

    init {
        initView {
            registerMain(getString(R.string.app_name), false) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.no_magic_pointer,
                            tipsId = R.string.no_magic_pointer_tips
                        ),
                        SwitchV("no_magic_pointer", true)
                    )
                } else {
                    TitleText(getString(R.string.a11_tips))
                }
                Line()
                TitleText(getString(R.string.reboot))
                @Suppress("DEPRECATION")
                TextSummaryArrow(
                    TextSummaryV(getString(R.string.reboot_system)) {
                        MIUIDialog(this@MainActivity) {
                            setTitle(R.string.warning)
                            setMessage(R.string.reboot_tips)
                            setLButton(R.string.cancel) {
                                dismiss()
                            }
                            setRButton(R.string.done) {
                                Utils.exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                            }
                        }.show()
                    }
                )
                Line()
                TitleText(getString(R.string.test))
                TextSummaryArrow(
                    TextSummaryV(
                        text = getString(R.string.switch_to_aosp_pointer),
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
                                showToast(getString(R.string.switch_to_aosp_pointer_success))
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast(buildString {
                                    append(getString(R.string.switch_to_aosp_pointer_failed))
                                    append(exceptionString)
                                })
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        textId = R.string.switch_to_magic_pointer,
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
                                showToast(getString(R.string.switch_to_magic_pointer_success))
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast(buildString {
                                    append(getString(R.string.switch_to_magic_pointer_failed))
                                    append(exceptionString)
                                })
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        textId = R.string.get_current_pointer_state,
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
                                showToast(buildString {
                                    append(getString(R.string.get_current_pointer_state_success))
                                    append(state)
                                })
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast(buildString {
                                    append(getString(R.string.get_current_pointer_state_failed))
                                    append(exceptionString)
                                })
                            }
                        }
                    )
                )
                TextSummaryArrow(
                    TextSummaryV(
                        textId = R.string.switch_pointer_style,
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
                                showToast(getString(R.string.switch_pointer_style_success))
                            } catch (e: Exception) {
                                val exceptionString = getStackTraceInfo(e)
                                showToast(buildString {
                                    append(getString(R.string.switch_pointer_style_failed))
                                    append(exceptionString)
                                })
                            }
                        }
                    )
                )
            }
        }
    }

    private fun showToast(string: String) {
        handler.post {
            Toast.makeText(this, string, Toast.LENGTH_LONG).show()
        }
    }
}
