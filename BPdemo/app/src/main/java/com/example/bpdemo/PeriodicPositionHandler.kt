package com.example.bpdemo

import android.content.Context
import android.os.Handler
import android.os.Looper
import locus.api.android.ActionBasics
import locus.api.android.features.periodicUpdates.UpdateContainer
import locus.api.android.objects.LocusVersion
import locus.api.android.utils.LocusUtils
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class PeriodicPositionHandler(context: Context) {

    private val handler : Handler = Handler(Looper.getMainLooper())

    private val ref : (() -> Unit) = {
        refreshContent()
    }
    private val context : Context
    init {
        this.context = context
    }

    private val host = "192.168.1.14"
    private val port = 5001

    private var cont = true

    private var lock = ReentrantLock()

    fun refreshContent()
    {
        try{
            Thread{
                Thread.sleep(5000)
                LocusUtils.getActiveVersion(context)?.let { lv ->
                    ActionBasics.getUpdateContainer(context, lv)?.let{uc ->
                        handleUpdate(lv ,uc)
                    } ?: run {
                        handleUpdate(lv ,null)
                    }
                } ?: run {
                    handleUpdate(null , null)
                }
            }.start()

        } finally {
            lock.lock()
            val cont1 = cont
            lock.unlock()
            if (cont1) handler.postDelayed(ref , TimeUnit.SECONDS.toMillis(5))
            else handler.removeCallbacks(ref)
        }
    }

    private fun handleUpdate(lv : LocusVersion?, uc: UpdateContainer?)
    {
        lock.lock()
        val cont1 = cont
        lock.unlock()
        println(cont1)
        if(!cont1) return
        if( lv == null || uc == null)
        {
            KtorClient().sendErr(host , port)
        } else
        {
            KtorClient().sendLoc(uc.locMyLocation, host ,port)
            println(uc.locMyLocation.latitude)

        }

    }

    fun stopUpdates()
    {
        lock.lock()
        cont = !cont
        lock.unlock()
    }
}