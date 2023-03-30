package com.attitude.designs.valtrackr.ui.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attitude.designs.valtrackr.model.news.Entry
import com.attitude.designs.valtrackr.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel  @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _response = MutableLiveData<List<Entry>>()
    val responseImages: LiveData<List<Entry>> get() = _response

    init {
        getnews()
    }

     fun getnews() = viewModelScope.launch {

             repository.getNews().let { response ->
                 if (response.isSuccessful) {
                     _response.postValue(response.body()!!.entries)
                 } else {
                     println("Error ${response.errorBody()}")
                 }
             }
    }

}