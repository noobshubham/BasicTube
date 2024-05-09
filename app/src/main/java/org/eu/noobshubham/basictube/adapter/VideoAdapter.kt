package org.eu.noobshubham.basictube.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.eu.noobshubham.basictube.R
import org.eu.noobshubham.basictube.model.BasicTubeVideo

class  VideoAdapter() {}

/*
class VideoAdapter(private val context: Context, private val videoList: List<BasicTubeVideo>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.videoView)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val channelTextView: TextView = itemView.findViewById(R.id.channel)
        val viewsTextView: TextView = itemView.findViewById(R.id.views)
        val ageTextView: TextView = itemView.findViewById(R.id.age)
        val likesTextView: TextView = itemView.findViewById(R.id.likes)
        val dividerView: View = itemView.findViewById(R.id.divider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_video_card, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]

        // Set data to views
        holder.thumbnailImageView.setImageResource(video.thumbnail)
        holder.titleTextView.text = video.title
        holder.channelTextView.text = video.channel
        holder.viewsTextView.text = video.views
        holder.ageTextView.text = video.age
        holder.likesTextView.text = video.likes
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}
*/