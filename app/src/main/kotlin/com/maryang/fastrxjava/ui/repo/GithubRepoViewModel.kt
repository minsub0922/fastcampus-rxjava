package com.maryang.fastrxjava.ui.repo

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.GithubRepo
import io.reactivex.android.schedulers.AndroidSchedulers

class GithubRepoViewModel {
    private val repository = GithubRepository()

    fun onClickStar(repo: GithubRepo) =
        (if (repo.star)
            repository.unstar(repo.owner.userName, repo.name)
        else
            repository.star(repo.owner.userName, repo.name))
            .observeOn(AndroidSchedulers.mainThread())
}
