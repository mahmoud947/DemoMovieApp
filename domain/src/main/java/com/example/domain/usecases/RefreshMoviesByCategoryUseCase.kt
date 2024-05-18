package com.example.domain.usecases

import com.example.core.base.BaseIOUseCase
import com.example.core.base.BaseSuspendOUseCase
import com.example.core.utils.Resource
import com.example.domain.models.MovieCategory
import com.example.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshMoviesByCategoryUseCase @Inject constructor(
    private val repository: MoviesRepository
) : BaseSuspendOUseCase<Flow<Resource<Unit>>> {
    override suspend fun invoke(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            val result = repository.refresh()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it))
        }
    }
}