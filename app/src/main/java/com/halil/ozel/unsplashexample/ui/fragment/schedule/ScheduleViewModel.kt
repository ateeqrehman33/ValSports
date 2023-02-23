package com.halil.ozel.unsplashexample.ui.fragment.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.allschedule.Event_
import com.halil.ozel.unsplashexample.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel  @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _response = MutableLiveData<List<Event_>>()
    val responseImages: LiveData<List<Event_>> get() = _response

    init {
        getAllSchedule()
    }

    private fun getAllSchedule() = viewModelScope.launch {
        repository.getSchedule().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.schedule.events)
            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

}