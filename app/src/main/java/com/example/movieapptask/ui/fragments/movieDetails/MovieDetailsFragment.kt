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
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModel
import com.example.core.utils.Resource
import com.example.core.utils.handle
import com.example.domain.models.Movie
import com.example.movieapptask.R
import com.example.movieapptask.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    override val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding:FragmentMovieDetailsBinding
    private val navArgs: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        val movieId = navArgs.movieId
        viewModel.getMovieById(movieId)
        observer()
        return binding.root
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collectLatest { resource: Resource<Movie> ->
                    resource.handle(onLoading = {

                    }, onSuccess = {
                        binding.movie = it
                        binding.executePendingBindings()
                    }, onError = {

                    })

                }
            }
        }
    }

}