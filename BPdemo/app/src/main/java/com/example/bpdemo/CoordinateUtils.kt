package com.example.bpdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler
import kotlin.concurrent.timerTask

class CoordinateUtils(textView: TextView, context: Context) {

    private fun checkAccLoc(context: Context) : Boolean {
        val succ = ContextCompat.checkSelfPermission(context , Manifest.permission.ACCESS_FINE_LOCATION)
        return succ == PackageManager.PERMISSION_GRANTED

    }

    private fun checkLocEn(context: Context) : Boolean {
        val locationManage = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(!locationManage.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            println("not enabled")
            return false
        }
        return true
    }

    val locationManger: FusedLocationProviderClient
    private val textView: TextView

    init {
        locationManger = LocationServices.getFusedLocationProviderClient(context)
        this.textView = textView
        this.textView.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun showWarningText( locEn: Boolean)
    {
        textView.visibility = View.VISIBLE
        if ( locEn ) textView.text = "Location provider is not Enabled"
        else textView.text = "Localization for this app was not allowed"
    }

    fun checkUtils(context: Context) : Boolean
    {
        val accLoc = checkAccLoc(context)
        val locEn = checkLocEn(context)
        if( !accLoc || !locEn)
        {
            showWarningText(locEn)
            return false
        }
        this.textView.visibility = View.INVISIBLE
        return true
    }

}