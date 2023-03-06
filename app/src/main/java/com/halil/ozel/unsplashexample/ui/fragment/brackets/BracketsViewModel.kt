package com.halil.ozel.unsplashexample.ui.fragment.brackets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.brackets.Standing_
import com.halil.ozel.unsplashexample.model.leagues.League_
import com.halil.ozel.unsplashexample.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BracketsViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    private val _response = MutableLiveData<List<Standing_>>()
    val responseImages: LiveData<List<Standing_>> get() = _response


    private val _responseLEague = MutableLiveData<List<League_>>()
    val responseLeague: LiveData<List<League_>> get() = _responseLEague


    init {
        getLeagues()
    }

    fun updateBracketsByTourId(selectedtourId: String){
        getAllImages(selectedtourId)
    }

    private fun getLeagues() = viewModelScope.launch {
        repository.getLeagues().let { response ->
            if(response.isSuccessful){
                _responseLEague.postValue(response.body()!!.data.leagues)

                var selectedtourId : String = ""

                for (league in response.body()!!.data.leagues ){
                    if(league.displayPriority.status.equals("force_selected")){
                        selectedtourId = league.tournaments[0].id
                        break
                    }
                    else if(league.displayPriority.status.equals("selected")){
                        selectedtourId = league.tournaments[0].id
                        break
                    }
                }

                getAllImages(selectedtourId)
            }
            else{
                println("Error ${response.errorBody()}")
            }
        }
    }

    private fun getAllImages(selectedtourId: String) = viewModelScope.launch {
        repository.getBrackets(selectedtourId).let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body()!!.data.standings)
            } else {
                println("Error ${response.errorBody()}")
            }
        }
    }

}