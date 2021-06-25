package com.dwtraining.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dwtraining.archcompmodule2.databinding.FragmentDetailMovieBinding
import com.dwtraining.base.BaseFragment
import com.dwtraining.viewmodels.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Giovani González
 * Created by Giovani on 2020-10-27.
 */
@AndroidEntryPoint
class DetailMovieFragment : BaseFragment() {

    private val detailViewModel: MovieDetailViewModel by viewModels()
    private var idMovie: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idMovie = arguments?.getLong("id") ?: 0L
    }

    override fun setContentView(container: ViewGroup?): View =
        FragmentDetailMovieBinding.inflate(layoutInflater, container, false).apply {
            viewmodel = detailViewModel.apply {
                getMovie(idMovie)
            }
            movie = detailViewModel.selectedMovie
            ratingBar.max = 10
            (requireActivity() as AppCompatActivity).apply {
                this.setSupportActionBar(animToolbar)
                this.title = "Detail screen"
                this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}