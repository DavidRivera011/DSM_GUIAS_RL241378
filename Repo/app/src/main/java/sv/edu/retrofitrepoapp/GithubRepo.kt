package sv.edu.retrofitrepoapp

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("html_url")
    val htmlUrl: String?
)
