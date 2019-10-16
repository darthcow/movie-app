package com.project.movieapp.extensions

import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.shortToast(message: String) =
    runOnUiThread { Toast.makeText(this, message,  Toast.LENGTH_SHORT).show() }

fun AppCompatActivity.longToast(message: String) =
    runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }

fun AppCompatActivity.resourceToast(@StringRes message: Int) =
    runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }


fun AppCompatActivity.displayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}