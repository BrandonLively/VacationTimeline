package com.wkdrabbit.vacationtimeline.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wkdrabbit.vacationtimeline.UserRepository
import com.wkdrabbit.vacationtimeline.models.Attraction

class AttractionViewModel(application: Application) : AndroidViewModel(application){
    private var repository: UserRepository =
        UserRepository(application)
    private var allAttraction: LiveData<List<Attraction>> = repository.getAllAttractions()

    fun insert(attraction: Attraction) {
        repository.insert(attraction)
    }

    fun update(attraction: Attraction) {
        repository.update(attraction)
    }

    fun delete(attraction: Attraction) {
        repository.delete(attraction)
    }

    fun deleteAllAttraction() {
        repository.deleteAllAttractions()
    }

    fun getAllAttraction(): LiveData<List<Attraction>> {
        return allAttraction
    }

    fun getAttractionById(id:Int):LiveData<List<Attraction>>{
        return repository.getAttractionsByItineraryId(id)
    }
}