package sv.edu.retrofitrepoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sv.edu.retrofitrepoapp.databinding.ItemRepoBinding

class RepoAdapter(
    private val repositories: MutableList<GithubRepo>
) : RecyclerView.Adapter<RepoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: RepoViewHolder,
        position: Int
    ) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    fun updateRepositories(newRepositories: List<GithubRepo>) {
        repositories.clear()
        repositories.addAll(newRepositories)
        notifyDataSetChanged()
    }
}
