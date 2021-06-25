package com.dwtraining.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwtraining.archcompmodule2.R
import com.dwtraining.archcompmodule2.databinding.ItemMoviesListBinding
import com.dwtraining.base.BaseFragment
import com.dwtraining.models.Movie

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-22.
 */
class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    private var movieItems = ArrayList<Movie>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesListBinding.inflate(inflater, parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.titleMovie.text = movieItems[position].title
        context?.let {
            Glide.with(it)
                .load(movieItems[position].poster_image_path)
                .into(holder.posterMovie)
            // Setting the popularity of the movie
            holder.popularityMovie.text =
                it.getString(R.string.popularity_text, movieItems[position].popularity)
            // Setting the rating of the movie
            holder.ratingMovie.text = context!!
                .getString(R.string.rating_text, movieItems[position].rating)
        }
        holder.parent.setOnClickListener {
            val bundle = bundleOf(BaseFragment.HAS_TOOLBAR_KEY to false, "id" to movieItems[position].id)
            it.findNavController().navigate(R.id.action_mainFragment_to_detailMovieFragment, bundle)
        }
    }

    override fun getItemCount(): Int = movieItems.size

    fun refreshDataMovies(list: List<Movie>) {
        movieItems = ArrayList(list)
        notifyDataSetChanged()
    }

    class MovieHolder(binding: ItemMoviesListBinding) : RecyclerView.ViewHolder(binding.root) {
        val parent = binding.root
        val posterMovie: ImageView = binding.imagePosterItem
        val titleMovie: TextView = binding.textTitleMovie
        val popularityMovie: TextView = binding.textPopularity
        val ratingMovie: TextView = binding.textRating
    }
}