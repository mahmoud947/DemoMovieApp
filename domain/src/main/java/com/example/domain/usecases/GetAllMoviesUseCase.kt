package com.example.domain.usecases

import com.example.core.base.BaseIOUseCase
import com.example.core.base.BaseOUseCase
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory
import com.example.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) : BaseOUseCase<Flow<Resource<List<Movie>>>> {
    override fun invoke(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading)
            val result = repository.getAllMovies()
            emit(Resource.Success(result))
        }.catch {
            emit(ExceptionHandler.resolveError(it))
        }
    }
}