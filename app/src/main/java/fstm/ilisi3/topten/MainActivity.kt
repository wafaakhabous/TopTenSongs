package fstm.ilisi3.topten
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        songAdapter = SongAdapter(emptyList())
        recyclerView.adapter = songAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(LastFmApiService::class.java)

        val call = apiService.getTopTracks(10)
        call.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(call: Call<TopTracksResponse>, response: Response<TopTracksResponse>) {
                if (response.isSuccessful) {
                    val topTracksResponse = response.body()
                    if (topTracksResponse != null) {
                        val topTracks = topTracksResponse.tracks.track.orEmpty()
                        println("JSON Response: ${response.body()}")

                        if (topTracks.isNotEmpty()) {
                            for (track in topTracks) {
                                println("Track Name: ${track.name}")
                                println("Artist Name: ${track.artist.name}")
                                println("Image URL: ${track.imageUrl}")
                            }
                            songAdapter = SongAdapter(topTracks)
                            recyclerView.adapter = songAdapter
                        } else {
                            println("Top tracks list is empty.")
                        }
                    } else {
                        println("Response body is null.")
                    }
                } else {
                    println("Response not successful. Code: ${response.code()}")
                    println("Error message: ${response.message()}")
                    // Additional logging for debugging
                    response.errorBody()?.let {
                        println("Error body: ${it.string()}")
                    }
                }
            }

            override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

