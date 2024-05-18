package com.example.domain.usecases

import com.example.core.base.BaseSuspendOUseCase
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) : BaseSuspendOUseCase<Flow<Resource<Unit>>> {
    override suspend fun invoke(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            repository.refresh()
            emit(Resource.Success(Unit))
        }.catch {
            emit(ExceptionHandler.resolveError(it))
        }
    }
}