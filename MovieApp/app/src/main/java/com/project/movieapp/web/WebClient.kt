package com.project.movieapp.web

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//    API key: fc621c22645579c003cd78bba01d4f70
class WebClient {

    private var retrofitBase = Retrofit.Builder()

        .baseUrl(URLConstants.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun movieService(): MovieService = retrofitBase.create(MovieService::class.java)

 object URLConstants{
    const val BASEURL = "https://api.themoviedb.org/3/"
     const val IMAGEURL = "https://image.tmdb.org/t/p/w780/"
     const val APIKEY = "fc621c22645579c003cd78bba01d4f70"

}
}