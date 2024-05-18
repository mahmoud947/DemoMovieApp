package com.example.domain.models

enum class MovieCategory(val endPoint:String) {
    NowPlaying("now_playing"),
    Popular("popular"),
    TopRated("top_rated"),
    Upcoming("upcoming")
}
