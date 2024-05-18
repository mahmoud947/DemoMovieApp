package com.example.data.mapper

import com.example.data.datasource.local.entity.MovieEntity
import com.example.data.datasource.remote.dtos.response.MovieRes
import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory

fun MovieRes.toEntity(movieCategory: MovieCategory): MovieEntity = MovieEntity(
    id = id ?: 0,
    adult = adult,
    backdropUrl = backdropUrl,
    genreIds = genreIds?: emptyList(),
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterUrl = posterUri,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    category = movieCategory.name,
)

fun MovieEntity.toDomain(): Movie = Movie(
    adult = adult ?: false,
    backdropUrl = backdropUrl.orEmpty(),
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0.0,
    posterUrl = posterUrl.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    title = title.orEmpty(),
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0

)