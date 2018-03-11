package com.herokuapp.trytov.jarvis

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

val appContext: Context = MainApplication.application

fun getStringResource(@StringRes stringRes: Int): String {
    return appContext.getString(stringRes)
}

fun getColorResource(colorRes: Int): Int {
    return ContextCompat.getColor(appContext, colorRes)
}

fun getDrawableResource(drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(appContext, drawableRes)
}

fun getDimen(dimenRes: Int): Float {
    return appContext.resources.getDimension(dimenRes)
}
