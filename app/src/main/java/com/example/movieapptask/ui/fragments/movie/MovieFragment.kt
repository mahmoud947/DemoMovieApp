package com.example.movieapptask.ui.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseFragment
import com.example.core.extensions.snackBar
import com.example.core.extensions.toast
import com.example.core.utils.Resource
import com.example.core.utils.handle
import com.example.domain.models.Movie
import com.example.movieapptask.R
import com.example.movieapptask.utils.recyclerview.WrapContentLinearLayoutManager
import com.example.movieapptask.animation.ZoomOutPageTransformer
import com.example.movieapptask.databinding.FragmentMovieBinding
import com.example.movieapptask.ui.fragments.movie.adapters.MovieAdapter
import com.example.movieapptask.ui.fragments.movie.adapters.TopRatedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    private lateinit var upcomingLayoutManager: LinearLayoutManager
    private lateinit var nowPlayingLayoutManager: LinearLayoutManager
    private lateinit var popularLayoutManager: LinearLayoutManager
    override val viewModel: MoviesViewModel
            by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        initView()

        observers()
        return binding.root
    }

    private fun initView() {
        upcomingLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        topRatedAdapter = TopRatedAdapter(interaction = object : TopRatedAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Movie) {
                viewModel.navigateToMovieDetails(item.id)
            }
        })
        val movieAdapterInteraction = object : MovieAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Movie) {
                viewModel.navigateToMovieDetails(item.id)
            }
        }
        upcomingAdapter = MovieAdapter(interaction = movieAdapterInteraction)
        nowPlayingAdapter = MovieAdapter(interaction = movieAdapterInteraction)
        popularAdapter = MovieAdapter(interaction = movieAdapterInteraction)

        binding.vpTopRated.apply {
            adapter = topRatedAdapter
            offscreenPageLimit = 5
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(ZoomOutPageTransformer())
        }

        binding.rvUpcoming.apply {
            adapter = upcomingAdapter
            layoutManager = upcomingLayoutManager
        }

        binding.rvPopular.apply {
            adapter = popularAdapter
            layoutManager = popularLayoutManager
        }

        binding.rvNowPlaying.apply {
            adapter = nowPlayingAdapter
            layoutManager = nowPlayingLayoutManager
        }
        binding.srl.isRefreshing = false

        binding.srl.setOnRefreshListener {
            viewModel.refresh()
        }

    }

    private fun observers() {
        observeTopRated()
        observeUpcoming()
        observePopular()
        observeNowPlaying()
        observeRefresh()
    }

    private fun observeRefresh() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshData.collectLatest { resources ->
                    resources.handle(onLoading = {
                        binding.srl.isRefreshing = false
                    }, onSuccess = {
                        binding.srl.isRefreshing = false
                        viewModel.getMovies()
                    }, onError = {
                        snackBar(it.message.toString())
                    })
                }
            }
        }
    }


    private fun observeTopRated() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRated.collectLatest { resource: Resource<List<Movie>> ->
                    resource.handle(onLoading = {

                    }, onSuccess = {

                        topRatedAdapter.submitList(it)
                    }, onError = {

                    })

                }
            }
        }
    }

    private fun observeUpcoming() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.upcoming.collectLatest { resource: Resource<List<Movie>> ->
                    resource.handle(onLoading = {
                    }, onSuccess = {
                        upcomingAdapter.submitList(it)
                        upcomingAdapter.isLoading = false
                    }, onError = {
                        upcomingAdapter.isLoading = false
                    })
                }
            }
        }
    }

    private fun observePopular() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popular.collectLatest { resource: Resource<List<Movie>> ->
                    resource.handle(onLoading = {
                    }, onSuccess = {
                        popularAdapter.submitList(it)
                        popularAdapter.isLoading = false
                    }, onError = {
                    })
                }
            }
        }
    }


    private fun observeNowPlaying() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nowPlaying.collectLatest { resource: Resource<List<Movie>> ->

                    resource.handle(onLoading = {

                    }, onSuccess = {
                        if (resource.isEmpty()) {
                            errorDialog.updateMessage("Looks like you have no movies. Try Sync now!")
                            errorDialog.updateButtonTitle("Sync")
                            errorDialog.startDialog()
                            errorDialog.onClick {
                                viewModel.refresh()
                                errorDialog.dismissDialog()
                            }
                        }
                        nowPlayingAdapter.submitList(it)
                        nowPlayingAdapter.isLoading = false
                    }, onError = {
                        toast(it.toString())
                    })
                }
            }
        }
    }
}