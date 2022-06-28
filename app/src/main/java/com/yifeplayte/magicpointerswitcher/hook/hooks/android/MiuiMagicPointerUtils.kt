package com.yifeplayte.magicpointerswitcher.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.magicpointerswitcher.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object MiuiMagicPointerUtils : BaseHook() {
    override fun init() {
        try {
            findMethod("android.magicpointer.util.MiuiMagicPointerUtils") {
                name == "isEnable"
            }.hookReturnConstant(false)
            XposedBridge.log("MagicPointerSwitcher: Hook MiuiMagicPointerUtils.isEnable success!")
        } catch (e: Throwable) {
            XposedBridge.log("MagicPointerSwitcher: Hook MiuiMagicPointerUtils.isEnable failed!")
        }
    }
}
