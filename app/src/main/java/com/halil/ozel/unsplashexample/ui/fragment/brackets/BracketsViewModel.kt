package com.halil.ozel.unsplashexample.ui.fragment.brackets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.ImageItem
import com.halil.ozel.unsplashexample.model.brackets.Standing_
import com.halil.ozel.unsplashexample.model.livematches.Event_
import com.halil.ozel.unsplashexample.model.unstarted.UpcomingEvent_
import com.halil.ozel.unsplashexample.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BracketsViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    private val _response = MutableLiveData<List<Standing_>>()
    val responseImages: LiveData<List<Standing_>> get() = _response

    init {
        getAllImages()
    }

    private fun getAllImages() = viewModelScope.launch {
        repository.getBrackets().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.standings)
            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

}