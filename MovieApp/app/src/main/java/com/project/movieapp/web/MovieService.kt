package com.project.movieapp.web

import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.beans.ListResponseBean
import com.project.movieapp.web.WebClient.URLConstants.APIKEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    //todo use interceptor later to insert api key and other optinal parameters
    //todo insert infinite scrolling method
    //add api key as parameter of fun
    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int = 1,@Query("api_key") apiKey: String = APIKEY): Call<ListResponseBean>

    @GET("movie/top_rated")
    fun getTop(@Query("page") page: Int = 1,@Query("api_key") apiKey: String = APIKEY): Call<ListResponseBean>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = APIKEY): Call<MovieDetailsBean>

    @GET("movie/{movie_id}/reviews")
    fun getListReviews(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = APIKEY): Call<MovieDetailsBean>

    @GET("review/{review_id}")
    fun getReview(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = APIKEY): Call<MovieDetailsBean>


}