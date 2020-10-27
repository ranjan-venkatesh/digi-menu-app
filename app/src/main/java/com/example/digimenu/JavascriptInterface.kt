package com.example.digimenu

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class JavascriptInterface(val payload: String?, val context: Context) {
    @JavascriptInterface
    fun getJSONData(): String? {
        return payload
    }

    @JavascriptInterface
    fun print(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}