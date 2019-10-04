package com.project.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.movieapp.R
import com.project.movieapp.extensions.loadUrl
import com.project.movieapp.web.ResultBean
import com.project.movieapp.web.WebClient.URLConstants

class PopularListAdapter(private val context: Context, val items: List<ResultBean>) :
    RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(LayoutInflater.from(context).inflate(R.layout.adapter_movie_list_item, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = items[position]

        with(currentItem){
            holder.movieTitle.text = "Title: $title"
            holder.releaseDate.text = "Release Date: $release_date"
            holder.averageScore.text = "Average Score: $vote_average"
            holder.moviePoster.loadUrl(URLConstants.IMAGEURL+poster_path)

        }
    }
}


class Holder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    //fixme refactor names later to reference adapter
    val movieTitle: TextView = itemview.findViewById(R.id.movieTitle)
    val releaseDate: TextView = itemview.findViewById(R.id.releaseDate)
    val averageScore: TextView = itemview.findViewById(R.id.averageScore)
    val moviePoster: ImageView = itemview.findViewById(R.id.moviePoster)
}