package com.attitude.designs.valtrackr.ui.main

import androidx.lifecycle.ViewModel
import com.attitude.designs.valtrackr.repository.ValoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ValoMainViewModel @Inject constructor(private val repository: ValoRepository) : ViewModel() {

}