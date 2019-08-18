package com.wkdrabbit.vacationtimeline.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wkdrabbit.vacationtimeline.UserRepository
import com.wkdrabbit.vacationtimeline.models.Itinerary

class ItineraryViewModel (application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allItineraries: LiveData<List<Itinerary>> = repository.getAllItineraries()

    fun insert(itinerary: Itinerary) {
        repository.insert(itinerary)
    }

    fun update(itinerary: Itinerary) {
        repository.update(itinerary)
    }

    fun delete(itinerary: Itinerary) {
        repository.delete(itinerary)
    }

    fun deleteAllItineraries() {
        repository.deleteAllItineraries()
    }

    fun getAllItineraries(): LiveData<List<Itinerary>> {
        return allItineraries
    }
}