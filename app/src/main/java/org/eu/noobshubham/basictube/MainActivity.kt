package org.eu.noobshubham.basictube

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eu.noobshubham.basictube.adapter.VideoAdapter
import org.eu.noobshubham.basictube.model.BasicTubeVideo

val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_ANON_KEY
) {
    install(Postgrest)
}

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        searchView = findViewById(R.id.searchView)
        progressBar = findViewById(R.id.progressBar)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from Supabase
        progressBar.visibility = ProgressBar.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val videos = fetchVideosFromDatabase()
            val adapter = VideoAdapter(this@MainActivity, videos)
            withContext(Dispatchers.Main) {
                progressBar.visibility = ProgressBar.GONE
                recyclerView.adapter = adapter
            }

            // Handling the code for search view.
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    // on below line we are checking
                    // if query exist or not.
                    searchView.clearFocus()
                    progressBar.visibility = ProgressBar.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        val queryResult = supabase.from("topten").select {
                            filter {
                                if (query != null) {
                                    textSearch("title_description_channel", query, TextSearchType.NONE)
                                }
                            }
                        }.decodeList<BasicTubeVideo>()

                        lifecycleScope.launch(Dispatchers.Main) {
                            progressBar.visibility = ProgressBar.GONE
                            if (queryResult.isEmpty()) {
                                // if query is empty we are displaying
                                // a toast message as no  data found..
                                Toast.makeText(this@MainActivity, "No item found..", Toast.LENGTH_LONG).show()
                                adapter.updateVideos(videos)
                            } else adapter.updateVideos(queryResult)
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // if query text is change in that case we
                    // are filtering our adapter with newText.
                    adapter.updateVideos(videos.filter {
                        it.title.contains(newText.toString(), ignoreCase = true)
                        it.channel.contains(newText.toString(), ignoreCase = true)
                        it.description.contains(newText.toString(), ignoreCase = true)
                    })
                    return false
                }
            })
            // exiting the searchview code.
        }
    }
}

suspend fun fetchVideosFromDatabase(): List<BasicTubeVideo> {
    val response = supabase.from("topten").select().decodeList<BasicTubeVideo>()
    return response.map {
        BasicTubeVideo(
            id = it.id,
            channel = it.channel,
            title = it.title,
            likes = it.likes,
            views = it.views,
            length = it.length,
            description = it.description,
            url = it.url
        )
    }
}