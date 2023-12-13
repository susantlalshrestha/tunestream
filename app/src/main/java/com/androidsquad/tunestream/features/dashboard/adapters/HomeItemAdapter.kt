package com.androidsquad.tunestream.features.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidsquad.tunestream.R

class HomeItemAdapter(val items: ArrayList<HomeItem>) :
    RecyclerView.Adapter<HomeItemAdapter.ItemViewHolder>() {

    data class HomeItem(val title: String, val list: ArrayList<*>)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = TrackItemAdapter(item.list)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
    }
}