package sv.edu.retrofitrepoapp

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import sv.edu.retrofitrepoapp.databinding.ItemRepoBinding

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemRepoBinding.bind(view)

    fun bind(repo: GithubRepo) {
        binding.tvRepoName.text =
            repo.name ?: "Sin nombre"

        binding.tvRepoDescription.text =
            repo.description ?: "Sin descripción"

        binding.tvRepoUrl.text =
            repo.htmlUrl ?: "URL no disponible"

        binding.tvRepoUrl.setOnClickListener {
            repo.htmlUrl?.let { url ->
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
                itemView.context.startActivity(intent)
            }
        }
    }
}
