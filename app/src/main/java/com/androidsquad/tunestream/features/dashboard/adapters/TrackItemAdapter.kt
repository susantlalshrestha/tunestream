package com.androidsquad.tunestream.features.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.services.model.Track
import com.androidsquad.tunestream.services.model.TrackItem
import com.squareup.picasso.Picasso

class TrackItemAdapter(private val tracks: ArrayList<*>) :
    RecyclerView.Adapter<TrackItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trackItem = tracks[position]
        var track: Track? = when(trackItem){
            is TrackItem -> trackItem.track
            is Track -> trackItem
            else -> null
        }
        if(track is Track) {
            holder.tvTrackTitle.text = track.name
            val artistNames = track.artists.joinToString(", ") { it.name }
            holder.tvArtistsNames.text = artistNames
            val images = track.album.images
            if (images.isNotEmpty()) {
                Picasso.get().load(images[0].url).placeholder(R.drawable.img_placeholder)
                    .into(holder.ivTrackImage)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTrackTitle: TextView = itemView.findViewById(R.id.tv_track_title)
        val tvArtistsNames: TextView = itemView.findViewById(R.id.tv_artists_names)
        val ivTrackImage: ImageView = itemView.findViewById(R.id.iv_track_image)
    }
}