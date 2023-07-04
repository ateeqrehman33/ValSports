package com.attitude.designs.valtrackr.ui.fragment.livematches.bottomsheet

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attitude.designs.valtrackr.model.leagues.League_
import com.attitude.designs.valtrackr.repository.ValoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottomSheetViewModel @Inject constructor(private val repository: ValoRepository) : ViewModel() {

    private val _response = MutableLiveData<List<League_>>()
    val responseLeagues: LiveData<List<League_>> get() = _response

    init {
        getAllImages()
    }
    @SuppressLint("CommitPrefEdits")
    private fun getAllImages() = viewModelScope.launch {
        repository.getLeagues().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.leagues)
            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

}