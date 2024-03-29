package com.attitude.designs.valtrackr.ui.fragment.livematches

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attitude.designs.valtrackr.model.livematches.Event_
import com.attitude.designs.valtrackr.model.unstarted.UpcomingEvent_
import com.attitude.designs.valtrackr.repository.ValoRepository
import com.attitude.designs.valtrackr.utils.Constants
import com.attitude.designs.valtrackr.utils.TinyDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LiveViewModel @Inject constructor(private val repository: ValoRepository, context: Context) : ViewModel() {
    private val _response = MutableLiveData<List<Event_>>()
    val responseLiveMatches: LiveData<List<Event_>> get() = _response


    private val upcoming_response = MutableLiveData<List<UpcomingEvent_>>()
    val responseUpcoming: LiveData<List<UpcomingEvent_>> get() = upcoming_response

    private var tinyDB : TinyDB = TinyDB(context)

    init {
        getAllImages()
        getUpcomingMatches()
    }

    fun getAllImages() = viewModelScope.launch {
        repository.getLiveEvents().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.schedule.events)

            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

    fun getUpcomingMatches() = viewModelScope.launch {

        val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)
        val s: String = TextUtils.join(",", list)

        if(list.isNotEmpty()){
            repository.getUpcomingMatches(s).let { response2 ->
                if (response2.isSuccessful) {
                    upcoming_response.postValue(response2.body()!!.data.unstarted.events)
                } else {
                    println("Error ${response2.errorBody()}")
                }
            }
        }
    }
}