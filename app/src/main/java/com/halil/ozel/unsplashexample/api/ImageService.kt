package com.halil.ozel.unsplashexample.api

import com.halil.ozel.unsplashexample.model.ImageItem
import com.halil.ozel.unsplashexample.model.allschedule.AllScheduleResponse_
import com.halil.ozel.unsplashexample.model.brackets.BracketsResponse_
import com.halil.ozel.unsplashexample.model.livematches.LiveMatchesResponse_
import com.halil.ozel.unsplashexample.model.unstarted.UpcomingEventListResponse_
import com.halil.ozel.unsplashexample.utils.Constants.ACCEPT_VERSION
import com.halil.ozel.unsplashexample.utils.Constants.AUTHORIZATION
import com.halil.ozel.unsplashexample.utils.Constants.CLIENT_ID
import com.halil.ozel.unsplashexample.utils.Constants.GET_BRACKETS
import com.halil.ozel.unsplashexample.utils.Constants.GET_EVENT_LIST
import com.halil.ozel.unsplashexample.utils.Constants.GET_LIVE_DETAILS
import com.halil.ozel.unsplashexample.utils.Constants.GET_SCHEDULE
import com.halil.ozel.unsplashexample.utils.Constants.VERSION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageService {

    @GET(GET_LIVE_DETAILS)
    suspend fun getAllImages(@Query("hl") hl: String,@Query("sport") sport: String): Response<LiveMatchesResponse_>

    @GET(GET_EVENT_LIST)
    suspend fun getUpcomingMatches(@Query("hl") hl: String,@Query("sport") sport: String,@Query("leagueId") leagueids : String): Response<UpcomingEventListResponse_>

    @GET(GET_SCHEDULE)
    suspend fun getSchedule(@Query("hl") hl: String,@Query("sport") sport: String,@Query("leagueId") leagueids : String): Response<AllScheduleResponse_>

    @GET(GET_BRACKETS)
    suspend fun getBrackets(@Query("hl") hl: String,@Query("sport") sport: String,@Query("tournamentId") leagueids : String): Response<BracketsResponse_>




//    @GET(END_POINT)
//    suspend fun getAllImages(): Response<LiveMatchesResponse_>
}