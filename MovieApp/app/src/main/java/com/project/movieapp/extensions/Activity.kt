package com.project.movieapp.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import java.net.InetAddress
import java.util.logging.Handler
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager



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
 fun AppCompatActivity.isNetworkConnected(): Boolean {
    val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}