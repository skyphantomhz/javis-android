package com.herokuapp.trytov.jarvis.extensions

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.showDialogOnlyAccept(context: Context, title: String, content: String) {
    AlertDialog.Builder(context).create().apply {
        setTitle(title)
        setCancelable(false)
        setMessage(content)
        setButton(AlertDialog.BUTTON_POSITIVE, "OK", { _: DialogInterface, _: Int ->
            finish()
        })
        show()
    }
}