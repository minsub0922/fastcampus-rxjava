package com.maryang.fastrxjava.ui

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.data.source.DefaultCallback
import com.maryang.fastrxjava.entity.GithubRepo
import retrofit2.Call
import retrofit2.Response

class GithubReposViewModel {
    private val repository = GithubRepository()

    fun getGithubRepos(
        onResponse: (List<GithubRepo>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        repository.getGithubRepos().enqueue(object : DefaultCallback<List<GithubRepo>>() {
            override fun onResponse(
                call: Call<List<GithubRepo>>,
                response: Response<List<GithubRepo>>
            ) {
                onResponse(response.body() ?: emptyList())
            }

            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                super.onFailure(call, t)
                onFailure(t)
            }
        })
    }
}
