package com.wkdrabbit.vacationtimeline.models

import android.net.Uri
import androidx.room.*

//Brandon Lively - 07/28/2019

//TODO: Add primary key relationships to models
@Entity(tableName = "attraction_table")
class Attraction(
    @PrimaryKey
    var attraction_id: Int,
    var itin_id: Int,
    var name: String,
    var startTime: Long = 3200000,
    var endTime: Long = 3200000,
    var alarmBefore: Long = 0,
    var alarmAfter: Long = 0,
    var description: String = "Description",
    var lat: String,
    var lng: String,
    var address: String = "Address",
    var phoneNum: String = "555-555-5555",
    var transportType: Int = 0,
    var transportEta: Long = 0,
    var transportLabel: String = "Car")





class User(var name: Uri){

    var uriString = ""

    init {
        uriString = name.toString()
    }

    fun getUri():Uri{
        return Uri.parse(uriString)
    }
}






@Entity(tableName = "itinerary_table")
class Itinerary(@PrimaryKey var itinerary_id:Int, var destinationName: String = "Destination", var timeVisited: Long = System.currentTimeMillis()/1000)