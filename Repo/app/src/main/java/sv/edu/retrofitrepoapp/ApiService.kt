package sv.edu.retrofitrepoapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}/repos")
    fun getRepositories(
        @Path("username") username: String
    ): Call<List<GithubRepo>>
}
