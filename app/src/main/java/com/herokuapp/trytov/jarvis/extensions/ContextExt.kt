package com.herokuapp.trytov.jarvis.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.showToast(data: String ) {
    Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
}

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}