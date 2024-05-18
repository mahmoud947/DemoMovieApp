package com.example.movieapptask.ui.fragments.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModel
import com.example.core.extensions.snackBar
import com.example.core.utils.Resource
import com.example.core.utils.handle
import com.example.domain.models.Movie
import com.example.movieapptask.R
import com.example.movieapptask.databinding.FragmentMovieDetailsBinding
import com.example.movieapptask.ui.fragments.movie.adapters.MovieAdapter
import com.example.movieapptask.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    override val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailsBinding
    private val navArgs: MovieDetailsFragmentArgs by navArgs()

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieLayoutManger: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movieId = navArgs.movieId
        initView()
        viewModel.getMovieById(movieId)
        observer()
        return binding.root
    }

    private fun initView() {
        movieLayoutManger =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter()

        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = movieLayoutManger
        }
    }

    private fun observer() {
        observeMovieData()
        observeMoviesData()
    }

    private fun observeMoviesData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collectLatest { resource: Resource<List<Movie>> ->
                    resource.handle(onLoading = {
                        movieAdapter.isLoading = true
                    }, onSuccess = {
                        movieAdapter.isLoading = false
                        movieAdapter.submitList(it)
                    }, onError = {
                        movieAdapter.isLoading = false
                        errorDialog.updateMessage(it.message.toString())
                        errorDialog.updateButtonTitle("Try again")
                        errorDialog.startDialog()
                        errorDialog.onClick {
                            viewModel.getMovieById(navArgs.movieId)
                            errorDialog.dismissDialog()
                        }
                    })

                }
            }
        }
    }

    private fun observeMovieData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collectLatest { resource: Resource<Movie> ->
                    resource.handle(onLoading = {

                    }, onSuccess = {
                        binding.movie = it
                        binding.executePendingBindings()
                    }, onError = {
                        errorDialog.updateMessage(it.message.toString())
                        errorDialog.updateButtonTitle("Try again")
                        errorDialog.startDialog()
                        errorDialog.onClick {
                            viewModel.getMovieById(navArgs.movieId)
                            errorDialog.dismissDialog()
                        }
                    })

                }
            }
        }
    }

}