package fstm.ilisi3.topten

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApiService {
    @GET("?method=chart.gettoptracks&api_key=2e624f74cdd8e7464fc5268e63993359&format=json")
    fun getTopTracks(@Query("limit") limit: Int): Call<TopTracksResponse>
}
