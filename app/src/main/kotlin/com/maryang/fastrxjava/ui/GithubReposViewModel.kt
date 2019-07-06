package com.maryang.fastrxjava.ui

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import com.maryang.fastrxjava.util.Operators
import com.maryang.fastrxjava.util.applySchedulersExtension
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GithubReposViewModel {
    private val repository = GithubRepository()

    fun getGithubRepos(): Single<List<GithubRepo>> =
        repository.getGithubRepos()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

    fun getUserRepo(): Maybe<User> =
        repository.getUser()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

    fun updatetUser(): Completable =
        repository.updateUser()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

//    fun getGithubRepos(
//        onResponse: (List<GithubRepo>) -> Unit,
//        onFailure: (Throwable) -> Unit
//    ) {
//        repository.getGithubRepos()
//            .enqueue(object : DefaultCallback<List<GithubRepo>>() {
//                override fun onResponse(
//                    call: Call<List<GithubRepo>>,
//                    response: Response<List<GithubRepo>>
//                ) {
//                    onResponse(response.body() ?: emptyList())
//                }
//
//                override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
//                    super.onFailure(call, t)
//                    onFailure(t)
//                }
//            })
//    }

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

            .applySchedulersExtension() //이게 최고지
//            .compose(Operators.applySchedulers())   //아래의 두줄을 없앨 수 있다...
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())  // 이 두 줄을 없애고 싶다.



    }

    private fun checkStar(owner: String, repo: String): Completable =
        repository.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
