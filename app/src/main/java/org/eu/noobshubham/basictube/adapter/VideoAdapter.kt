package org.eu.noobshubham.basictube.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import org.eu.noobshubham.basictube.R
import org.eu.noobshubham.basictube.model.BasicTubeVideo

class VideoAdapter(private val context: Context, private var videoList: List<BasicTubeVideo>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val channelTextView: TextView = itemView.findViewById(R.id.channel)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val likesTextView: TextView = itemView.findViewById(R.id.likes)
        val viewsTextView: TextView = itemView.findViewById(R.id.views)
        val playerView: PlayerView = itemView.findViewById(R.id.videoView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_video_card, parent, false)
        return VideoViewHolder(view)
    }

    @OptIn(UnstableApi::class)
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]

        // Set data to views
        holder.channelTextView.text = video.channel
        holder.titleTextView.text = video.title
        holder.likesTextView.text = video.likes.toString() + " Likes"
        holder.viewsTextView.text = video.views.toString() + " Views"

        // Set ExoPlayer for each VideoView
        val exoPlayer = ExoPlayer.Builder(context).build()
        holder.playerView.player = exoPlayer
        holder.playerView.controllerAutoShow = false

        // Prepare video URL and other configurations for ExoPlayer
        val videoUrl = video.url
        // Configure ExoPlayer with video URL
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        // Prepare ExoPlayer
        exoPlayer.playWhenReady = false
        // exoPlayer.prepare()
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateVideos(newVideos: List<BasicTubeVideo>) {
        videoList = newVideos
        notifyDataSetChanged()
    }
}