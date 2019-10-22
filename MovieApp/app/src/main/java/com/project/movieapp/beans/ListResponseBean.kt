package com.project.movieapp.beans

import com.project.movieapp.web.WebClient
import java.io.Serializable

data class ListResponseBean(
    var page: Int,
    var results: ArrayList<ResultBean>,
    var total_pages: Int,
    var total_results: Int
):Serializable

data class ResultBean(
    var adult: Boolean,
    var backdrop_path: String,
    var genre_ids: List<Int>,
    var id: Int,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int
):Serializable
{
    fun getPosterPath(): String = WebClient.URLConstants.IMAGEURL+poster_path
    fun getbackPath(): String = WebClient.URLConstants.IMAGEURL+backdrop_path

}