package com.project.movieapp.web

import retrofit2.Call
import retrofit2.http.GET


interface MovieService {
//todo use interceptor later to insert api key and other optinal parameters
@GET("movie/popular?api_key=fc621c22645579c003cd78bba01d4f70&language=en-US&page=1")
    fun getPopular(): Call<ResponseBean>

}