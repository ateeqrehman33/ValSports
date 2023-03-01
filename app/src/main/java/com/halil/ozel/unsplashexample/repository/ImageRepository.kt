package com.halil.ozel.unsplashexample.repository

import com.halil.ozel.unsplashexample.api.ImageService
import javax.inject.Inject

class ImageRepository @Inject constructor(private val api: ImageService) {
    suspend fun getAllImages() = api.getAllImages("en-GB","val")
    //suspend fun getAllImages() = api.getAllImages()
    suspend fun getUpcomingMatches(list: String) = api.getUpcomingMatches("en-GB","val",list)

    suspend fun getUpcomingMatches() = api.getUpcomingMatches("en-GB","val","109551178413356399,106109559530232966,107019646737643925,107566795186957938")


    suspend fun getSchedule() = api.getSchedule("en-GB","val","109551178413356399,106109559530232966,107019646737643925,107566795186957938")

    suspend fun getBrackets() = api.getBrackets("en-GB","val","109710937834457925")

    suspend fun getLeagues() = api.getLeagues("en-GB","val")

}