package com.androidsquad.tunestream.features.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.services.model.Playlist
import com.squareup.picasso.Picasso

class PlaylistItemAdapter(val playlists: ArrayList<Playlist> = arrayListOf()) :
    RecyclerView.Adapter<PlaylistItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.tvPlaylistName.text = playlist.name
        holder.tvPlaylistOwner.text = playlist.owner.display_name
        val images = playlist.images
        if (images.isNotEmpty()) {
            Picasso.get().load(images[0].url).placeholder(R.drawable.img_placeholder)
                .into(holder.vwBackground)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlaylistName: TextView = itemView.findViewById(R.id.tv_playlist_name)
        val tvPlaylistOwner: TextView = itemView.findViewById(R.id.tv_playlist_owner)
        val vwBackground: ImageView = itemView.findViewById(R.id.iv_playlist_image)
    }
}