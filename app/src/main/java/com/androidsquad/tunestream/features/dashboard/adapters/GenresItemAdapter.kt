package com.androidsquad.tunestream.features.dashboard.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.features.settings.SettingsActivity
import com.androidsquad.tunestream.services.model.Genre
import com.androidsquad.tunestream.services.model.Track
import com.androidsquad.tunestream.services.model.TrackItem
import com.squareup.picasso.Picasso
import kotlin.random.Random

class GenresItemAdapter(val genres: ArrayList<Genre> = arrayListOf()) :
    RecyclerView.Adapter<GenresItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val genre = genres[position]
        holder.tvTitle.text = genre.name
        holder.vwBackground.setBackgroundColor(
            Color.rgb(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            )
        )
        holder.itemView.setOnClickListener {
            SettingsActivity.start(holder.itemView.context, genre.id, genre.name)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val vwBackground: View = itemView.findViewById(R.id.vw_background)
    }
}