//package com.example.data.datasource.remote.mediator
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.example.data.datasource.local.MovieDatabase
//import com.example.data.datasource.local.entity.MovieEntity
//import com.example.data.datasource.remote.service.MovieService
//import com.example.data.mapper.toEntity
//import com.example.domain.models.MovieCategory
//import retrofit2.HttpException
//import java.io.IOException
//
//@OptIn(ExperimentalPagingApi::class)
//class MovieRemoteMediator(
//    private val db: MovieDatabase,
//    private val service: MovieService
//) : RemoteMediator<Int, MovieEntity>() {
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieEntity>
//    ): MediatorResult {
//        return try {
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(
//                    endOfPaginationReached = true
//                )
//
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if(lastItem == null) {
//                        1
//                    } else {
//                        (lastItem.index / state.config.pageSize) + 1
//                    }
//                }
//            }
//
//            val movies = service.getMoviesByCategory(
//                page = loadKey,
//                category = MovieCategory.upcoming.name
//            ).results
//
//            db.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    db.movieDao.clearAll()
//                }
//                val moviesEntities = movies.mapIndexed { index, movieRes ->
//                    movieRes.toEntity(movieCategory = MovieCategory.upcoming, index = index)
//                }
//                db.movieDao.upsertAll(moviesEntities)
//            }
//
//            MediatorResult.Success(
//                endOfPaginationReached = movies.isEmpty()
//            )
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//}
