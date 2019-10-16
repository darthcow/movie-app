package com.project.movieapp.web

import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.beans.ListResponseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieService {
    //todo use interceptor later to insert api key and other optinal parameters
    //todo insert infinite scrolling method
    @GET("movie/popular?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US&page=1")
    fun getPopular(): Call<ListResponseBean>

    @GET("movie/top_rated?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US&page=1")
    fun getTop(): Call<ListResponseBean>

    @GET("movie/{movie_id}?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Call<MovieDetailsBean>
//todo
    @GET("movie/{movie_id}/reviews?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US")
    fun getListReviews(@Path("movie_id") movieId: Int): Call<MovieDetailsBean>
//todo

    @GET("review/{review_id}?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US")
    fun getReview(@Path("movie_id") movieId: Int): Call<MovieDetailsBean>


}