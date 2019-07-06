package com.maryang.fastrxjava.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.entity.GithubRepo
import kotlinx.android.synthetic.main.item_github_repo.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk21.listeners.onClick

class GithubReposAdapter : RecyclerView.Adapter<GithubReposAdapter.RepoViewHolder>() {

    var items: List<GithubRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_repo, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: GithubRepo) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
                repoStar.imageResource =
                    if (repo.star) R.drawable.baseline_star_24
                    else R.drawable.baseline_star_border_24
                onClick { }
                repoStar.onClick { }
            }
        }
    }
}
