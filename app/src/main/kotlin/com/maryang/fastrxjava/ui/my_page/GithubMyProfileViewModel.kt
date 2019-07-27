package com.maryang.fastrxjava.ui.my_page

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.MyInfo
import com.maryang.fastrxjava.util.applySchedulersExtension
import io.reactivex.Single

class GithubMyProfileViewModel {
    private val repository = GithubRepository()

    fun getMyInfo(): Single<List<MyInfo>> =
            repository.getMyInfo()
                .applySchedulersExtension()
}
