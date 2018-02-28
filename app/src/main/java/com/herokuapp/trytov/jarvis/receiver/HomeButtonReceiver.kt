package com.herokuapp.trytov.jarvis.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class HomeButtonReceiver : BroadcastReceiver() {
    val SYSTEM_DIALOG_REASON_KEY = "reason"
    val SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions"
    val SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps"
    val SYSTEM_DIALOG_REASON_HOME_KEY = "homekey"

    override fun onReceive(p0: Context, intent: Intent) {
        Log.d("HomeButton", "You have just hold home button")
        val action = intent.action
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            val reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                Log.e("HomeButton", "action:" + action + ",reason:" + reason);
            } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                Log.e("HomeButton", "mission complete");
            }
        }
    }
}
