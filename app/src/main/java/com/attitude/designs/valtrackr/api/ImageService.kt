package com.attitude.designs.valtrackr.api

import com.attitude.designs.valtrackr.model.allschedule.AllScheduleResponse_
import com.attitude.designs.valtrackr.model.brackets.BracketsResponse_
import com.attitude.designs.valtrackr.model.leagues.LeaguesResponse_
import com.attitude.designs.valtrackr.model.livematches.LiveMatchesResponse_
import com.attitude.designs.valtrackr.model.unstarted.UpcomingEventListResponse_
import com.attitude.designs.valtrackr.utils.Constants.GET_BRACKETS
import com.attitude.designs.valtrackr.utils.Constants.GET_EVENT_LIST
import com.attitude.designs.valtrackr.utils.Constants.GET_LEAGUES
import com.attitude.designs.valtrackr.utils.Constants.GET_LIVE_DETAILS
import com.attitude.designs.valtrackr.utils.Constants.GET_SCHEDULE
import retrofit2.Response
import retrofit2.http.GET
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

    @GET(GET_LEAGUES)
    suspend fun getLeagues(@Query("hl") hl: String,@Query("sport") sport: String): Response<LeaguesResponse_>


}