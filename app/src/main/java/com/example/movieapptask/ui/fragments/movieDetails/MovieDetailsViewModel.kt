package com.example.movieapptask.ui.fragments.movieDetails

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.utils.ErrorModel
import com.example.core.utils.Resource
import com.example.domain.models.Movie
import com.example.domain.usecases.GetAllMoviesUseCase
import com.example.domain.usecases.GetMoviesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMoviesByIdUseCase: GetMoviesByIdUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : BaseViewModel() {
    private val _movie = MutableStateFlow<Resource<Movie>>(Resource.Loading)
    val movie: StateFlow<Resource<Movie>> get() = _movie


    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val movies: StateFlow<Resource<List<Movie>>> get() = _movies


    init {
        getAllMovies()
    }

    override fun handleCoroutineException(exception: Throwable) {
        super.handleCoroutineException(exception)
        showErrorMessage.value = ErrorModel(message = exception.message.toString(), button = "Ok!")
    }

    fun getMovieById(id: Int) {
        launchCoroutine(Dispatchers.IO) {
            getMoviesByIdUseCase(id).onEach { _movie.emit(it) }.launchIn(viewModelScope)
        }
    }

    fun getAllMovies() {
        getAllMoviesUseCase().onEach {
            _movies.emit(it)
        }.launchIn(viewModelScope)
    }
}