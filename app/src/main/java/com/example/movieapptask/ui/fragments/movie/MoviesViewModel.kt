package com.example.movieapptask.ui.fragments.movie

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.utils.ErrorModel
import com.example.core.utils.Resource
import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory
import com.example.domain.usecases.GetMoviesByCategoryUseCase
import com.example.domain.usecases.RefreshMoviesUseCase
import com.example.startupproject.ui.base.NavigationCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase,
    private val refreshMoviesUseCase: RefreshMoviesUseCase
) : BaseViewModel() {

    init {
        refresh()
        getMovies()
    }

    private val _topRated = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRated: StateFlow<Resource<List<Movie>>> get() = _topRated

    private val _popular = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val popular: StateFlow<Resource<List<Movie>>> get() = _popular


    private val _upcoming = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val upcoming: StateFlow<Resource<List<Movie>>> get() = _upcoming


    private val _nowPlaying = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val nowPlaying: StateFlow<Resource<List<Movie>>> get() = _nowPlaying

    private val _refreshData = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val refreshData: StateFlow<Resource<Unit>> get() = _refreshData

    override fun handleCoroutineException(exception: Throwable) {
        super.handleCoroutineException(exception)
        showErrorMessage.value = ErrorModel(message = exception.message.toString(), button = "Ok!")
    }

    fun getMovies() {
        launchCoroutine(Dispatchers.IO) {
            val upcoming = async { getMoviesByCategoryUseCase(MovieCategory.Upcoming) }
            val popular = async { getMoviesByCategoryUseCase(MovieCategory.Popular) }
            val topRated = async { getMoviesByCategoryUseCase(MovieCategory.TopRated) }
            val nowPlaying = async { getMoviesByCategoryUseCase(MovieCategory.NowPlaying) }

            topRated.await().onEach {
                _topRated.emit(it)
            }.launchIn(viewModelScope)
            upcoming.await().onEach {
                _upcoming.emit(it)
            }.launchIn(viewModelScope)
            popular.await().onEach {
                _popular.emit(it)
            }.launchIn(viewModelScope)
            nowPlaying.await().onEach {
                _nowPlaying.emit(it)
            }.launchIn(viewModelScope)
        }
    }

    fun refresh(){
        launchCoroutine(Dispatchers.IO) {
            refreshMoviesUseCase().onEach {
                _refreshData.emit(it)
            }.launchIn(viewModelScope)
        }
    }
    fun navigateToMovieDetails(movieId: Int) {
        navigationCommand.value = NavigationCommand.To(
            MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(movieId)
        )
    }
}