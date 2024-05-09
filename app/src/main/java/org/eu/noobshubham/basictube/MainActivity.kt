package org.eu.noobshubham.basictube

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.C
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.eu.noobshubham.basictube.adapter.VideoAdapter
import org.eu.noobshubham.basictube.model.BasicTubeVideo

val supabase = createSupabaseClient(
    supabaseUrl = org.eu.noobshubham.basictube.BuildConfig.SUPABASE_URL,
    supabaseKey = org.eu.noobshubham.basictube.BuildConfig.SUPABASE_ANON_KEY
) {
    install(Postgrest)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from Supabase
        lifecycleScope.launch(Dispatchers.Main) {
            val videos = fetchVideosFromDatabase()
            val adapter = VideoAdapter(this@MainActivity, videos)
            adapter.apply {
                notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
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
