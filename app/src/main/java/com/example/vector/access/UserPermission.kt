package com.example.vector.access

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class UserPermission(context: Context, activity: Activity) {
    private val con = context
    private var act = activity

    private fun hasPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            con,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        val permission = mutableListOf<String>()
        if (!hasPermissions()) {
            permission.add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (permission.isNotEmpty()) {
            ActivityCompat.requestPermissions(act, permission.toTypedArray(), 0)
        }
    }
}