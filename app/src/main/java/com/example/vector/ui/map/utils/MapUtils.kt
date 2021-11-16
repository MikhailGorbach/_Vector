package com.example.vector.ui.map.utils

import android.content.Context
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun Context.setClickable(
    clicked: Boolean,
    normalFloatingActionBtn: FloatingActionButton,
    terrainFloatingActionBtn: FloatingActionButton,
    satelliteFloatingActionBtn: FloatingActionButton
) {
    if (!clicked) {
        normalFloatingActionBtn.isClickable = true
        terrainFloatingActionBtn.isClickable = true
        satelliteFloatingActionBtn.isClickable = true
    } else {
        normalFloatingActionBtn.isClickable = false
        terrainFloatingActionBtn.isClickable = false
        satelliteFloatingActionBtn.isClickable = false
    }
}

fun Context.setVisibility(
    clicked: Boolean,
    normalFloatingActionBtn: FloatingActionButton,
    terrainFloatingActionBtn: FloatingActionButton,
    satelliteFloatingActionBtn: FloatingActionButton
) {
    if (!clicked) {
        normalFloatingActionBtn.isVisible = false
        terrainFloatingActionBtn.isVisible = false
        satelliteFloatingActionBtn.isVisible = false
    } else {
        normalFloatingActionBtn.isVisible = true
        terrainFloatingActionBtn.isVisible = true
        satelliteFloatingActionBtn.isVisible = true
    }
}
