package com.herokuapp.trytov.jarvis.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.herokuapp.trytov.jarvis.R;

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        this.setCustomAnimations(R.anim.enter_to_left,R.anim.exit_to_left,R.anim.enter_to_left,R.anim.exit_to_left)
        action()
    }.commit()
}
