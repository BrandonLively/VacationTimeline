package com.wkdrabbit.vacationtimeline.localStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wkdrabbit.vacationtimeline.models.Itinerary

@Dao
interface ItineraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itinerary: Itinerary)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(itinerary:Itinerary)

    @Delete
    fun delete(itinerary: Itinerary)

    @Query("DELETE FROM itinerary_table")
    fun deleteAllItineraries()

    @Query("SELECT * FROM itinerary_table")
    fun getAllItineraries(): LiveData<List<Itinerary>>
}