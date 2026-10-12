package sv.edu.retrofitrepoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sv.edu.retrofitrepoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repoAdapter: RepoAdapter

    private val repositories = mutableListOf<GithubRepo>()

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        initRecyclerView()

        binding.searchDogs.setOnQueryTextListener(this)
    }

    private fun initRecyclerView() {
        repoAdapter = RepoAdapter(repositories)

        binding.listDogs.layoutManager =
            LinearLayoutManager(this)

        binding.listDogs.adapter = repoAdapter
    }

    private fun searchRepositories(username: String) {

        repoAdapter.updateRepositories(emptyList())

        apiService.getRepositories(username)
            .enqueue(object : Callback<List<GithubRepo>> {

                override fun onResponse(
                    call: Call<List<GithubRepo>>,
                    response: Response<List<GithubRepo>>
                ) {
                    if (response.isSuccessful) {

                        val repos = response.body().orEmpty()

                        repoAdapter.updateRepositories(repos)

                        if (repos.isEmpty()) {
                            showMessage(
                                "El usuario no tiene repositorios públicos."
                            )
                        }

                    } else {
                        repoAdapter.updateRepositories(emptyList())

                        when (response.code()) {
                            404 -> showMessage(
                                "No se encontró el usuario de GitHub."
                            )

                            403 -> showMessage(
                                "Límite de solicitudes alcanzado o acceso restringido."
                            )

                            else -> showMessage(
                                "Error HTTP: ${response.code()}"
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<GithubRepo>>,
                    t: Throwable
                ) {
                    showMessage(
                        "Error de conexión: ${
                            t.localizedMessage ?: "sin conexión"
                        }"
                    )
                }
            })
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        val username = query.trim()

        if (username.isNotEmpty()) {
            searchRepositories(username)
            binding.searchDogs.clearFocus()
        } else {
            showMessage("Escribe un usuario de GitHub.")
        }

        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return true
    }
}
