package com.example.bpdemo

import android.content.Context
import android.location.Location
import android.location.LocationRequest
import android.widget.TextView
import com.google.android.gms.tasks.Task


class CoordinateManger(context: Context , warningTextView: TextView) {

    private val coordinateUtils : CoordinateUtils
    private val coordConverter : CoordConverter

    init {
        coordinateUtils = CoordinateUtils(warningTextView , context)
        coordConverter = CoordConverter()
    }

    fun getLocation(context:Context): Task<Location>?
    {
        if (! coordinateUtils.checkUtils(context)) return null
        return coordinateUtils.locationManger.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY , null)

    }

    fun printLocOnTXField( textView: TextView , context: Context)
    {
        val locTask = getLocation(context)
        if( locTask == null)
        {
            textView.text = "Err"
            return
        }

        locTask.addOnSuccessListener {location ->
            if( location == null ) textView.text = " on succ null was returned"
            else textView.text = " Latidute: ${location.latitude} Longitude: ${location.longitude} \n UTM: ${coordConverter.degreesToUTM(location)}"

        }

        //textView.text = "${locTask.isComplete}"
    }

}