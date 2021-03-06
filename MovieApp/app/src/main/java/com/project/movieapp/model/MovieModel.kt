package com.project.movieapp.model


import com.project.movieapp.beans.MovieDetailsBean
import io.realm.RealmList
import io.realm.RealmObject
import java.io.Serializable

//todo use open class if data class doesn't work
//
open class MovieModel()
//    var adult: Boolean? = false,
//    var backdrop_path: String = "",
//    var belongs_to_collection: Boolean? = null,
//    var budget: Int = 0,
//    var genres: RealmList<Genre>? = null,
//    var homepage: String = "",
//    var id: Int = 0,
//    var imdb_id: String = "",
//    var original_language: String = "",
//    var original_title: String = "",
//    var overview: String = "",
//    var popularity: Double = 0.0,
//    var poster_path: String = "",
//    var production_companies: RealmList<ProductionCompany>? = null,
//    var production_countries: RealmList<ProductionCountry>? = null,
//    var release_date: String = "",
//    var revenue: Int = 0,
//    var runtime: Int = 0,
//    var spoken_languages: RealmList<SpokenLanguage>? = null,
//    var status: String = "",
//    var tagline: String = "",
//    var title: String = "",
//    var video: Boolean? = null,
//    var vote_average: Double = 0.0,
//    var vote_count: Int = 0
////    var model: MovieDetailsBean? = null
//) : RealmObject()
//
//
//open class Genre(
//    var id: Int = 0,
//    var name: String = ""
//) : RealmObject()
//
//open class ProductionCompany(
//    var id: Int = 0,
//    var logo_path: String = "",
//    var name: String = "",
//    var origin_country: String = ""
//) : RealmObject()
//
//open class ProductionCountry(
//    var iso_3166_1: String = "",
//    var name: String = ""
//) : RealmObject()
//
//open class SpokenLanguage(
//    var iso_639_1: String = "",
//    var name: String = ""
//) : RealmObject()