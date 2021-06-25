package com.dwtraining.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwtraining.adapters.MoviesAdapter
import com.dwtraining.models.Movie
import dagger.hilt.android.internal.managers.ViewComponentManager

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-23.
 */

@BindingAdapter("android:movieItems")
fun setItems(recycler: RecyclerView, items: LiveData<List<Movie>>) {
    recycler.adapter?.let { adapter ->
        if (adapter is MoviesAdapter) {
            adapter.refreshDataMovies(items.value ?: emptyList())
        }
    }
}

@BindingAdapter("android:setAdapter")
fun setAdapter(recycler: RecyclerView, adapter: MoviesAdapter?) {
    adapter?.let {
        recycler.adapter = it
        recycler.layoutManager = GridLayoutManager(recycler.context as ViewComponentManager.FragmentContextWrapper, 2)
    }
}

@BindingAdapter("android:imageURL")
fun setImage(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView).load(it).into(imageView)
    }
}