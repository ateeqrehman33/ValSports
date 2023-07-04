package com.attitude.designs.valtrackr.repository

import com.attitude.designs.valtrackr.api.ValoApiService
import javax.inject.Inject

class ValoRepository @Inject constructor(private val api: ValoApiService) {
    suspend fun getLiveEvents() = api.getLiveEvents("en-GB","val")
    suspend fun getUpcomingMatches(list: String) = api.getUpcomingMatches("en-GB","val",list)
    suspend fun getSchedule(list: String) = api.getSchedule("en-GB","val",list)
    suspend fun getBrackets(selectedtourId: String) = api.getBrackets("en-GB","val",selectedtourId)
    suspend fun getLeagues() = api.getLeagues("en-GB","val")
    suspend fun getNews() = api.getNews("bltb730eada072bdbf4","cs61908494445448f776bbdbc7","production","cachebust","article_type","banner_settings.banner","date","description","external_link","video_link","title","uid","url","url","date","en-gb")

}