package com.example.retrosheetadmin.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.example.retrosheetadmin.BuildConfig.DEBUG
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "mohamadjavadx"

fun <T> T.log(title: String? = null, lifecycle: Lifecycle? = null): T {
    if (DEBUG) {
        Log.d(TAG, makeMessageText(title = title, message = this.toString(), lifecycle = lifecycle))
    }

    return this
}

fun <T> T.logSimple(title: String? = null): T {
    if (DEBUG) {
        Log.d(TAG, "${if (!title.isNullOrBlank()) "$title-> " else ""}${this.toString()}")
    }

    return this
}

fun <T> T.logThread(title: String? = null): T {
    if (DEBUG) {
        Log.d(
            TAG,
            "${if (!title.isNullOrBlank()) "$title-> " else ""}onThread: ${Thread.currentThread().name}"
        )
    }

    return this
}

private val currentDateAndTime: String
    get() =
        SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss.ms",
            Locale("us")
        ).format(Calendar.getInstance().time)

private fun makeMessageText(title: String?, message: String, lifecycle: Lifecycle?): String =
    """ 
    ------------------------------------------------------------------------------------------------
    $TAG  |  $currentDateAndTime  |  ${Thread.currentThread().name}  |  ${lifecycle?.currentState}
    ------------------------------------------------------------------------------------------------
    ${if (!title.isNullOrBlank()) "$title-> " else ""}$message
    ------------------------------------------------------------------------------------------------
    """