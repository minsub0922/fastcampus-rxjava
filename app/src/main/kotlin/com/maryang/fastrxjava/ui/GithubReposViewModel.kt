package com.maryang.fastrxjava.ui

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.GithubRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GithubReposViewModel {
    private val repository = GithubRepository()

    var searchText = ""

    fun searchGithubRepos(search: String): Single<List<GithubRepo>> {
        searchText = search
        return Single.create<List<GithubRepo>> { emitter ->
            repository.searchGithubRepos(searchText)
                .subscribe({
                    Completable.merge(
                        it.map { repo ->
                            checkStar(repo.owner.userName, repo.name)
                                .doOnComplete { repo.star = true }
                                .onErrorComplete()
                        }
                    ).subscribe {
                        emitter.onSuccess(it)
                    }
                }, {})
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun checkStar(owner: String, repo: String): Completable =
        repository.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
