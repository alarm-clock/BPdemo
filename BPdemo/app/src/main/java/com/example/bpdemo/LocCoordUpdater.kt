package com.example.bpdemo

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import kotlinx.coroutines.Runnable
import locus.api.android.utils.IntentHelper
import locus.api.android.utils.LocusConst

class LocCoordUpdater(intent: Intent , textView: TextView) : Runnable {

    private var run: Boolean = true
    val intent: Intent
    val textView : TextView
    init{
        this.intent = intent
        this.textView = textView
    }

    fun stopUpdates() {run = false}

    override fun run() {

        while( run )
        {
            Thread.sleep(5000)
            val locMapCen = IntentHelper.getLocationFromIntent(intent , LocusConst.INTENT_EXTRA_LOCATION_MAP_CENTER)
            if( locMapCen != null) textView.text = "Map Centre at: ${locMapCen.latitude}   ${locMapCen.longitude}"
            else textView.text = " ERR "
        }
        run = true
    }
}