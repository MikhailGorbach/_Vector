package com.example.vector.utils

import android.app.Activity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

fun Activity.getCustomColor(color: Int): Int {
    return ContextCompat.getColor(applicationContext, color)
}

fun Activity.setBtnTextColor(btn: AppCompatButton, color: Int) {
    btn.setTextColor(getCustomColor(color))
}
