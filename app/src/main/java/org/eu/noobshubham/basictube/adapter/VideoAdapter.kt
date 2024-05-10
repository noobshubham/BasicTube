package org.eu.noobshubham.basictube.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import org.eu.noobshubham.basictube.R
import org.eu.noobshubham.basictube.model.BasicTubeVideo

class VideoAdapter(private val context: Context, private val videoList: List<BasicTubeVideo>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

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

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]

        // Set data to views
        holder.channelTextView.text = video.channel
        holder.titleTextView.text = video.title
        holder.likesTextView.text = video.likes.toString() + " Likes"
        holder.viewsTextView.text = video.views.toString() + " Views"

        // Set up click listener on PlayerView
        val player = ExoPlayer.Builder(context).build()
        holder.playerView.player = player
        holder.playerView.controllerAutoShow = false
        val mediaItem = MediaItem.fromUri(video.url)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}