package com.example.bpdemo

import android.location.Location
import kotlin.math.*

//https://github.com/truonghinh/Geo-Coordinate-Conversion-Java/tree/master
class CoordConverter {

    private fun getLetForUtm( Lat: Double) : Char
    {
        if (Lat<-72)
            return 'C';
        else if (Lat<-64)
            return 'D';
        else if (Lat<-56)
            return 'E';
        else if (Lat<-48)
            return 'F';
        else if (Lat<-40)
            return 'G';
        else if (Lat<-32)
            return 'H';
        else if (Lat<-24)
            return 'J';
        else if (Lat<-16)
            return 'K';
        else if (Lat<-8)
            return 'L';
        else if (Lat<0)
            return 'M';
        else if (Lat<8)
            return 'N';
        else if (Lat<16)
            return 'P';
        else if (Lat<24)
            return 'Q';
        else if (Lat<32)
            return 'R';
        else if (Lat<40)
            return 'S';
        else if (Lat<48)
            return 'T';
        else if (Lat<56)
            return 'U';
        else if (Lat<64)
            return 'V';
        else if (Lat<72)
            return 'W';
        else
            return 'X';
    }
//Easting=0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))^/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))*0.9996*6399593.62/Math.pow((1+Math.pow(0.0820944379, 2)*Math.pow(Math.cos(Lat*Math.PI/180), 2)), 0.5)*(1+ Math.pow(0.0820944379,2)/2*Math.pow((0.5*Math.log((1+Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180))/(1-Math.cos(Lat*Math.PI/180)*Math.sin(Lon*Math.PI/180-(6*Zone-183)*Math.PI/180)))),2)*Math.pow(Math.cos(Lat*Math.PI/180),2)/3)+500000;
    fun degreesToUTM( location: Location) : String
    {
        val lon = location.longitude
        val lat = location.latitude
        val zone = floor( lon/6+31).toInt()
        val letter = getLetForUtm( lat)
        //val easting = 0.5*log((1+cos(lat* PI /180)*sin(lon*PI /180 - (6 * zone -183) * PI /180))   )
        var Easting=0.5* ln((1+Math.cos(lat*Math.PI/180)*Math.sin(lon*Math.PI/180-(6*zone-183)*Math.PI/180))/(1-Math.cos(lat*Math.PI/180)*Math.sin(lon*Math.PI/180-(6*zone-183)*Math.PI/180))) *0.9996*6399593.62/Math.pow((1+Math.pow(0.0820944379, 2.0)*Math.pow(Math.cos(lat*Math.PI/180),2.0
        )), 0.5)*(1+ Math.pow(0.0820944379, 2.0
        )/2*Math.pow((0.5*Math.log((1+Math.cos(lat*Math.PI/180)*Math.sin(lon*Math.PI/180-(6*zone-183)*Math.PI/180))/(1-Math.cos(lat*Math.PI/180)*Math.sin(lon*Math.PI/180-(6*zone-183)*Math.PI/180)))),2.0
        )*Math.pow(Math.cos(lat*Math.PI/180), 2.0)/3)+500000;
        Easting=Math.round(Easting*100)*0.01;

        var Northing = (atan(tan(lat*Math.PI/180) / cos((lon*Math.PI/180-(6*zone -183)*Math.PI/180))) -lat*Math.PI/180)*0.9996*6399593.625/ sqrt(1+0.006739496742* cos(
            lat * Math.PI / 180
        ).pow(2.0)
        ) *(1+0.006739496742/2* (0.5 * ln(
            (1 + cos(lat * Math.PI / 180) * sin((lon * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180))) / (1 - cos(
                lat * Math.PI / 180
            ) * sin((lon * Math.PI / 180 - (6 * zone - 183) * Math.PI / 180)))
        )).pow(2.0) * cos(lat * Math.PI / 180).pow(2.0))+0.9996*6399593.625*(lat*Math.PI/180-0.005054622556*(lat*Math.PI/180+ sin(2*lat*Math.PI/180) /2)+4.258201531e-05*(3*(lat*Math.PI/180+ sin(2*lat*Math.PI/180) /2)+ sin(2*lat*Math.PI/180) * cos(
            lat * Math.PI / 180
        ).pow(2.0))/4-1.674057895e-07*(5*(3*(lat*Math.PI/180+ sin(2*lat*Math.PI/180) /2)+ sin(2*lat*Math.PI/180) * cos(lat * Math.PI / 180).pow(
            2.0
        ))/4+ sin(2*lat*Math.PI/180) * cos(lat * Math.PI / 180).pow(2.0) * cos(lat * Math.PI / 180).pow(2.0))/3)

        if ( letter < 'M') Northing += 10000000
        Northing = round(Northing*100)*0.1

        return "$zone$letter $Easting $Northing"


    }
}