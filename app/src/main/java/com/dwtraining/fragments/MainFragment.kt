package com.dwtraining.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.dwtraining.adapters.MoviesAdapter
import com.dwtraining.archcompmodule2.R
import com.dwtraining.archcompmodule2.databinding.FragmentMainBinding
import com.dwtraining.base.BaseFragment
import com.dwtraining.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-22.
 */
@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun setContentView(container: ViewGroup?): View =
        FragmentMainBinding.inflate(layoutInflater, container, false).apply {
            viewmodel = moviesViewModel.apply {
                isRefreshing.observe(viewLifecycleOwner, {
                    swipeRefreshMovies.isRefreshing = it
                })
            }
            adapter = MoviesAdapter()
            lifecycleOwner = viewLifecycleOwner
            binding = this
            executePendingBindings()
        }.root

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        showBackIconOnToolbar()
        setHasOptionsMenu(true)
        setTitle(getString(R.string.movies_list))
        binding.swipeRefreshMovies.setOnRefreshListener {
            if (binding.swipeRefreshMovies.isRefreshing) {
                moviesViewModel.getMovies(true)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_menu_sort_by_name -> moviesViewModel.sortMoviesByName()
            R.id.action_menu_sort_by_popularity -> moviesViewModel.sortMoviesByPopularity()
            else -> moviesViewModel.sortMoviesByRating()
        }
        return super.onOptionsItemSelected(item)
    }
}