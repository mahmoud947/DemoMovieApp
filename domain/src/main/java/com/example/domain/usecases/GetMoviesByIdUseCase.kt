package com.example.domain.usecases

import com.example.core.base.BaseSuspendIOUseCase
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Movie
import com.example.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesByIdUseCase @Inject constructor(
    private val repository: MoviesRepository
) : BaseSuspendIOUseCase<Int, Flow<Resource<Movie>>> {
    override suspend fun invoke(input: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading)
            val result = repository.getMoviesById(input)
            emit(Resource.Success(result))
        }.catch {
            emit(ExceptionHandler.resolveError(it))
        }
    }
}