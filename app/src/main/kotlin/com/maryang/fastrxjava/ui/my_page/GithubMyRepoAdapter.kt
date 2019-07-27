package com.maryang.fastrxjava.ui.my_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.MyInfo
import com.maryang.fastrxjava.ui.repo.GithubRepoActivity
import kotlinx.android.synthetic.main.item_github_repo.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk21.listeners.onClick

class GithubMyRepoAdapter : RecyclerView.Adapter<GithubMyRepoAdapter.RepoViewHolder>() {

    var items: List<MyInfo> = emptyList()
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

        fun bind(repo: MyInfo) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
            }
        }
    }
}
