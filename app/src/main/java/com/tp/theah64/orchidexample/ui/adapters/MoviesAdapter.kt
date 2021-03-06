package com.tp.theah64.orchidexample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.theah64.orchidexample.data.remote.search.SearchResponse
import com.tp.theah64.orchidexample.databinding.ItemMovieBinding

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
    fun appendMovies(newMovies: List<SearchResponse.Movie>) {

        // loop through new movies and if not exist add to list
        newMovies.forEach { newMovie ->
            // checking if new movie exist in the current list
            val isNewMovie = this.movies
                .filter { oldMovie -> oldMovie.imdbId == newMovie.imdbId }
                .isEmpty()

            if (isNewMovie) {
                this.movies.add(newMovie)
            }
        }
    }

    /**
     * Clears all movies from the list and updates the adapter
     */
    fun clearMovies() {
        this.movies.clear()
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movie = movies[position]
        holder.binding.callback = callback
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback.onMovieClicked(it, movies[layoutPosition])
            }
        }
    }

    interface Callback {
        fun onMovieClicked(root: View, movie: SearchResponse.Movie)
    }
}
