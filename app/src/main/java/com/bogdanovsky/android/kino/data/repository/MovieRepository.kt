package com.bogdanovsky.android.kino.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bogdanovsky.android.kino.data.database.MovieDatabase
import com.bogdanovsky.android.kino.data.database.toMovie
import com.bogdanovsky.android.kino.data.database.toMovieList
import com.bogdanovsky.android.kino.data.network.ImdbApi
import com.bogdanovsky.android.kino.data.network.toActorList
import com.bogdanovsky.android.kino.data.network.toDatabaseMovieList
import com.bogdanovsky.android.kino.domain.Actor
import com.bogdanovsky.android.kino.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse


class MovieRepository(private val movieDatabase: MovieDatabase) {
    val movies: LiveData<List<Movie>> = Transformations.map(movieDatabase.mdbDao.getMovies()) {
        it.toMovieList()
    }

    fun getMovieById(id: String): Movie {
//        Log.i("FrMoVeMo", "getMovieById. movieDatabase = $movieDatabase")
//        Log.i("FrMoVeMo", "getMovieById. mdbDao = ${movieDatabase.mdbDao}")
//        Log.i("FrMoVeMo", "getMovieById. getById(id) = ${movieDatabase.mdbDao.getMovieById(id)}")
//        return movieDatabase.mdbDao.getMovieById(id).toMovie()
        return movies.value?.find { it.id == id } ?: throw Exception("can't get movie by id from MovieRepository")
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val imdbMoviesList = ImdbApi.retrofitService.getTop250Movies().items
            movieDatabase.mdbDao.insertAll(imdbMoviesList.toDatabaseMovieList())
        }
    }

    suspend fun getActorsByMovieId(movieId: String) : List<Actor> {
        return withContext(Dispatchers.IO) {
            ImdbApi.retrofitService.getFullCast(movieId).actors.toActorList()
               // .awaitResponse().body()?.actors?.toActorList()
        }
    }
}

//
//import android.content.Context
//import android.util.Log
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import kotlinx.serialization.decodeFromString
//import kotlinx.serialization.json.Json
//
//interface MovieRepository {
//    suspend fun loadMovies(): List<Movie>
//    suspend fun loadMovie(movieId: Int): Movie?
//}
//
//internal class JsonMovieRepository(private val context: Context) : MovieRepository {
//    private val jsonFormat = Json { ignoreUnknownKeys = true }
//    private var movies: List<Movie>? = null
//
//    init {
//        Log.i("TAG", "Repository init block")
//    }
//
//    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
//        val cachedMovies = movies
//        if (cachedMovies != null) {
//            cachedMovies
//        } else {
//            val moviesFromJson = loadMoviesFromJsonFile()
//            movies = moviesFromJson
//            moviesFromJson
//        }
//    }
//
//    private suspend fun loadMoviesFromJsonFile(): List<Movie> {
//        val genresMap = loadGenres()
//        val actorsMap = loadActors()
//
//        val data = readAssetFileToString("data.json")
//        return parseMovies(data, genresMap, actorsMap)
//    }
//
//    private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
//        Log.i("TAG", "loadGenres function called")
//        val data = readAssetFileToString("genres.json")
//        Log.i("TAG", "loadGenres function called 2")
//        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
//        jsonGenres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }
//    }
//
//    private fun readAssetFileToString(fileName: String): String {
//        Log.i("TAG", "readAssetFileToString function called")
//        val stream = context.assets.open(fileName)
//        Log.i("TAG", "readAssetFileToString function called 2")
//        return stream.bufferedReader().readText()
//    }
//
//    private suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
//        val data = readAssetFileToString("people.json")
//        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
//
//        jsonActors.map { jsonActor ->
//            Actor(
//                id = jsonActor.id,
//                name = jsonActor.name,
//                imageUrl = jsonActor.profilePicture
//            )
//        }
//    }
//
//    private fun parseMovies(
//        jsonString: String,
//        genreData: List<Genre>,
//        actors: List<Actor>
//    ): List<Movie> {
//        val genresMap = genreData.associateBy(Genre::id)
//        val actorsMap = actors.associateBy(Actor::id)
//
//        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(jsonString)
//
//        return jsonMovies.map { jsonMovie ->
//            Movie(
//                id = jsonMovie.id,
//                title = jsonMovie.title,
//                storyLine = jsonMovie.overview,
//                imageUrl = jsonMovie.posterPicture,
//                detailImageUrl = jsonMovie.backdropPicture,
//                rating = (jsonMovie.ratings / 2).toInt(),
//                reviewCount = jsonMovie.votesCount,
//                pgAge = if (jsonMovie.adult) 16 else 13,
//                runningTime = jsonMovie.runtime,
//                genres = jsonMovie.genreIds.map { id ->
//                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
//                },
//                actors = jsonMovie.actors.map { id ->
//                    actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
//                },
//                isLiked = false
//            )
//        }
//    }
//
//    override suspend fun loadMovie(movieId: Int): Movie? {
//        val cachedMovies = movies ?: loadMovies()
//        return cachedMovies.find { it.id == movieId }
//    }
//
//    private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
//        return this ?: throw createThrowable()
//    }
//}
