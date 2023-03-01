package com.halil.ozel.unsplashexample.ui.fragment.livematches.bottomsheet

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.leagues.League_
import com.halil.ozel.unsplashexample.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottomSheetViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _response = MutableLiveData<List<League_>>()
    val responseImages: LiveData<List<League_>> get() = _response


    init {
        getAllImages()
    }


    @SuppressLint("CommitPrefEdits")
    private fun getAllImages() = viewModelScope.launch {
        repository.getLeagues().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.leagues)



                for (league in response.body()!!.data.leagues){
                    if(league.displayPriority.status.equals("selected") || league.displayPriority.status.equals("force_selected")){

                    }
                }



            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

}