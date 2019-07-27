package com.maryang.fastrxjava.ui.my_page

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.MyRepos
import com.maryang.fastrxjava.ui.repo.GithubRepoActivity
import kotlinx.android.synthetic.main.item_github_my_repos.view.*
import kotlinx.android.synthetic.main.item_github_repo.view.*
import kotlinx.android.synthetic.main.item_github_repo.view.repoDescription
import kotlinx.android.synthetic.main.item_github_repo.view.repoName
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk21.listeners.onClick

class GithubMyRepoAdapter : RecyclerView.Adapter<GithubMyRepoAdapter.RepoViewHolder>() {

    var items: List<MyRepos> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_my_repos, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: MyRepos) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
                profileImage.background = ShapeDrawable(OvalShape())
                profileImage.clipToOutline = true
                Glide.with(this)
                    .load(repo.owner.avatarUrl)
                    .into(profileImage)
            }
        }
    }
}
