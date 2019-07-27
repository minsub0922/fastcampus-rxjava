package com.maryang.fastrxjava.ui.my_page

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.MyInfo
import com.maryang.fastrxjava.entity.MyRepos
import com.maryang.fastrxjava.util.applySchedulersExtension
import io.reactivex.Single

class GithubMyReposViewModel {
    private val repository = GithubRepository()

    fun getMyRepos(): Single<List<MyRepos>> =
            repository.getMyRepos()
                .applySchedulersExtension()

    fun getMyProfile(): Single<MyInfo> =
            repository.getMyProfile()
                .applySchedulersExtension()
}
