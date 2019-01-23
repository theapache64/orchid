package com.tp.orchid.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tp.orchid.data.remote.search.SearchResponse
import com.tp.orchid.databinding.ItemMovieBinding

class MoviesAdapter(
    context: Context,
    val callback: Callback
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movies: MutableList<SearchResponse.Movie> = mutableListOf()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMovieBinding.inflate(layoutInflater, parent, false)
        )

    /**
     * Sets movie list and update the adapter
     */
    fun setMovies(movies: List<SearchResponse.Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    /**
     * Clears all movies from the list and updates the adapter
     */
    fun clearMovies() {
        this.movies.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movie = movies[position]
        holder.binding.callback = callback
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onMovieClicked(movie: SearchResponse.Movie)
    }
}
