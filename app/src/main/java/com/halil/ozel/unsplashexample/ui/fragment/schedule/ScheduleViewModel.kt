package com.halil.ozel.unsplashexample.ui.fragment.schedule

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.allschedule.Event_
import com.halil.ozel.unsplashexample.repository.ImageRepository
import com.halil.ozel.unsplashexample.utils.Constants
import com.halil.ozel.unsplashexample.utils.TinyDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleViewModel  @Inject constructor(private val repository: ImageRepository, context: Context) : ViewModel() {

    private val _response = MutableLiveData<List<Event_>>()
    val responseImages: LiveData<List<Event_>> get() = _response

    private var tinyDB : TinyDB = TinyDB(context)


    init {
        getAllSchedule()
    }

     fun getAllSchedule() = viewModelScope.launch {

         val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)
         val s: String = TextUtils.join(",", list)

         if(list.isNotEmpty()) {

             repository.getSchedule(s).let { response ->
                 if (response.isSuccessful) {
                     _response.postValue(response.body()!!.data.schedule.events)
                 } else {
                     println("Error ${response.errorBody()}")
                 }
             }

         }else{

             repository.getSchedule().let { response ->
                 if (response.isSuccessful) {
                     _response.postValue(response.body()!!.data.schedule.events)
                 } else {
                     println("Error ${response.errorBody()}")
                 }
             }
         }


    }

}