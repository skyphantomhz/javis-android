package com.herokuapp.trytov.jarvis.extensions

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.herokuapp.trytov.jarvis.appContext

fun View.setHideKeyBoardListener() {
    this.setOnTouchListener { _, _ ->
        val imm = appContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if(imm.isActive)
            imm.hideSoftInputFromWindow(windowToken, 0)
        false
    }
}
