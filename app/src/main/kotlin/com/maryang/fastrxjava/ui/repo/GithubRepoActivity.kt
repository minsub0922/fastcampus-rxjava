package com.maryang.fastrxjava.ui.repo

import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.base.BaseActivity
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.event.DataObserver
import com.maryang.fastrxjava.ui.user.UserActivity
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.activity_github_repo.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk21.listeners.onClick


class GithubRepoActivity : BaseActivity() {

    companion object {
        private const val KEY_REPO = "KEY_REPO"

        fun start(context: Context, repo: GithubRepo) {
            context.startActivity(
                context.intentFor<GithubRepoActivity>(
                    KEY_REPO to repo
                )
            )
        }
    }

    private val viewModel: GithubRepoViewModel by lazy {
        GithubRepoViewModel()
    }
    private lateinit var repo: GithubRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repo)
        intent.getParcelableExtra<GithubRepo>(KEY_REPO).let {
            this.repo = it
            supportActionBar?.run {
                title = it.name
                setDisplayHomeAsUpEnabled(true)
            }
            showRepo(it)
            setOnClickListener()
        }
    }

    private fun showRepo(repo: GithubRepo) {
        Glide.with(this)
            .load(repo.owner.avatarUrl)
            .into(ownerImage)
        ownerName.text = repo.owner.userName
        starCount.text = repo.stargazersCount.toString()
        watcherCount.text = repo.watchersCount.toString()
        forksCount.text = repo.forksCount.toString()
        showStar(repo.star)
    }

    private fun setOnClickListener() {
        star.onClick { clickStar() }
        ownerImage.onClick { clickOwner() }
        ownerName.onClick { clickOwner() }
    }

    private fun clickStar() {
        repo.star.not().let {
            showStar(it)
            starCount.text.toString().toInt().let { count ->
                starCount.text = (if (it) count + 1 else count - 1).toString()
            }
        }
        viewModel.onClickStar(repo)
            .subscribe(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    repo.apply {
                        star = !star
                    }.let {
                        showStar(it.star)
                        DataObserver.post(it)
                    }
                }

                override fun onError(e: Throwable) {
                    showStar(repo.star)
                    starCount.text = repo.stargazersCount.toString()
                }
            })
    }

    private fun clickOwner() {
        UserActivity.start(this, repo.owner)
    }

    private fun showStar(show: Boolean) {
        star.imageResource =
            if (show) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
    }
}
