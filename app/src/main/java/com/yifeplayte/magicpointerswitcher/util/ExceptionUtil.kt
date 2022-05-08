package com.yifeplayte.magicpointerswitcher.util

import java.io.PrintWriter
import java.io.StringWriter

object ExceptionUtil {
    fun getStackTraceInfo(e: Exception): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        return try {
            e.printStackTrace(pw)
            pw.flush()
            sw.flush()
            sw.toString()
        } catch (ex: Exception) {
            "异常信息转换错误"
        } finally {
            try {
                pw.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            try {
                sw.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}