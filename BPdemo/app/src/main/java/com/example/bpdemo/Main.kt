package com.example.bpdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import locus.api.android.objects.LocusInfo
import locus.api.android.objects.LocusVersion
import locus.api.android.utils.IntentHelper
import locus.api.android.utils.LocusConst
import locus.api.android.utils.LocusUtils
import locus.api.objects.extra.Location
import kotlin.system.exitProcess
import android.widget.Button
import locus.api.android.features.periodicUpdates.UpdateContainer
import locus.api.android.features.sendToApp.SendMode
import locus.api.android.features.sendToApp.SendToAppBase
import locus.api.android.features.sendToApp.SendToAppHelper


class MainActivity : AppCompatActivity() {

    fun opennedNormally(context: Context)
    {
        if( !LocusInfo().isRunning)
        {
            LocusUtils.callStartLocusMap(this)
        }
        moveTaskToBack(true)
        exitProcess(-1)
    }


    override fun onCreate(savedInstanceState: Bundle?){
        val version = LocusUtils.getAvailableVersions(this)

        if( version.isEmpty() ) return

        super.onCreate(savedInstanceState)

        if( IntentHelper.isIntentMainFunction(intent))
        {
            setContentView(R.layout.buttoninlocus)
            val tx1 = findViewById<TextView>(R.id.buttonInLocusTX1)
            val tx2 = findViewById<TextView>(R.id.buttonInLocusTX2)
            val tx3 = findViewById<TextView>(R.id.buttonInLocusTX3)
            val tx4 = findViewById<TextView>(R.id.buttonInLocusTX4)

            val pph = PeriodicPositionHandler(this)
            IntentHelper.handleIntentMainFunction(this , intent, object : IntentHelper.OnIntentReceived
            {
                @SuppressLint("SetTextI18n")
                override fun onReceived(lv: LocusVersion, locGps: Location?, locMapCenter: Location?) {

                    tx1.text = "Recieved intent from Locus Map"
                    if( locMapCenter != null)
                    {
                        tx2.text = "MapCentre Latitude: ${locMapCenter.latitude} Longitude: ${locMapCenter.longitude}"
                    } else tx2.text = "MapCnetre wasn't provided"

                    if( locGps != null )
                    {
                        tx3.text = "Gps Latitude: ${locGps.latitude} Longitude: ${locGps.longitude}"
                    } else tx3.text = " Gps location wasn't provided"

                    val btn = findViewById<Button>(R.id.buttonINLocusBT1)
                    val btn2 = findViewById<Button>(R.id.buttonInLocusBT2)
                    val btn3 = findViewById<Button>(R.id.buttonInLocusBT3)
                    var updateContainer = UpdateContainer()

                    btn.setOnClickListener {
                        Thread{
                            if(locGps != null) KtorClient().sendLoc(locGps,"192.168.1.14" , 5001)
                        }.start()
                    }
                    btn2.setOnClickListener {
                        pph.refreshContent()

                    }
                    btn3.setOnClickListener {
                        pph.stopUpdates()
                    }

                }

                override fun onFailed() {
                    tx1.text = " ERR "
                }
            })
        } else if( IntentHelper.isIntentPointTools(intent))
        {
            PeriodicPositionHandler(this).refreshContent()
        }
        else opennedNormally(this )
    }

    override fun on
}