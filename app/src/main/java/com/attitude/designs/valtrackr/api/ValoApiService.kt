package com.attitude.designs.valtrackr.api

import com.attitude.designs.valtrackr.model.allschedule.AllScheduleResponse_
import com.attitude.designs.valtrackr.model.brackets.BracketsResponse_
import com.attitude.designs.valtrackr.model.leagues.LeaguesResponse_
import com.attitude.designs.valtrackr.model.livematches.LiveMatchesResponse_
import com.attitude.designs.valtrackr.model.news.NewsResponse
import com.attitude.designs.valtrackr.model.unstarted.UpcomingEventListResponse_
import com.attitude.designs.valtrackr.utils.Constants.GET_BRACKETS
import com.attitude.designs.valtrackr.utils.Constants.GET_EVENT_LIST
import com.attitude.designs.valtrackr.utils.Constants.GET_LEAGUES
import com.attitude.designs.valtrackr.utils.Constants.GET_LIVE_DETAILS
import com.attitude.designs.valtrackr.utils.Constants.GET_NEWS
import com.attitude.designs.valtrackr.utils.Constants.GET_SCHEDULE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ValoApiService {

    @GET(GET_LIVE_DETAILS)
    suspend fun getLiveEvents(@Query("hl") hl: String,@Query("sport") sport: String): Response<LiveMatchesResponse_>
    @GET(GET_EVENT_LIST)
    suspend fun getUpcomingMatches(@Query("hl") hl: String,@Query("sport") sport: String,@Query("leagueId") leagueids : String): Response<UpcomingEventListResponse_>
    @GET(GET_SCHEDULE)
    suspend fun getSchedule(@Query("hl") hl: String,@Query("sport") sport: String,@Query("leagueId") leagueids : String): Response<AllScheduleResponse_>
    @GET(GET_BRACKETS)
    suspend fun getBrackets(@Query("hl") hl: String,@Query("sport") sport: String,@Query("tournamentId") leagueids : String): Response<BracketsResponse_>
    @GET(GET_LEAGUES)
    suspend fun getLeagues(@Query("hl") hl: String,@Query("sport") sport: String): Response<LeaguesResponse_>
    @GET(GET_NEWS)
    suspend fun getNews(@Header("api_key")token: String ,@Header("access_token") token2: String ,
                         @Query("environment") environment: String,
                        @Query("cachebust") cachebust: String,
                        @Query("only[BASE][]") articletype: String,
                        @Query("only[BASE][]") banner_settings_banner: String,
                        @Query("only[BASE][]") date: String,
                        @Query("only[BASE][]") description: String,
                        @Query("only[BASE][]") external_link: String,
                        @Query("only[BASE][]") video_link: String,
                        @Query("only[BASE][]") title: String,
                        @Query("only[BASE][]") uid: String,
                        @Query("only[BASE][]") url1: String,
                        @Query("only[banner_settings.banner]") url: String,
                        @Query("desc") date_: String,
                        @Query("locale") en_gb: String,
    ): Response<NewsResponse>

}