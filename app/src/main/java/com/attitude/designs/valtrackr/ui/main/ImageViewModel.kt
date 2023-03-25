package com.attitude.designs.valtrackr.ui.main

import androidx.lifecycle.ViewModel
import com.attitude.designs.valtrackr.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

}