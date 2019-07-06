package com.maryang.fastrxjava.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.base.BaseApplication
import com.maryang.fastrxjava.entity.GithubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_github_repos.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class GithubReposActivity : AppCompatActivity() {

    private val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    val searchSubject = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        refreshLayout.setOnRefreshListener { searchLoad(viewModel.searchText, false) }

        Log.d(BaseApplication.TAG, "current thread: ${Thread.currentThread().name}")

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just io thread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.io())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(
                    BaseApplication.TAG,
                    "just computation thread: ${Thread.currentThread().name}"
                )
            }
            .subscribeOn(Schedulers.computation())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just trampoline thread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.trampoline())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just newThread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just newThread2: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe()

        val thread = Executors.newSingleThreadExecutor()
        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just from: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.from(thread))
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just from2: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.from(thread))
            .subscribe()

        subscribeSearchSubscribe()
        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                searchSubject.onNext(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun subscribeSearchSubscribe() {
        searchSubject
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchLoad(it, true)
            }
    }

    private fun searchLoad(search: String, showLoading: Boolean) {
        if (showLoading)
            showLoading()
        viewModel.searchGithubRepos(search)
            .subscribe(object : DisposableSingleObserver<List<GithubRepo>>() {
                override fun onSuccess(t: List<GithubRepo>) {
                    hideLoading()
                    adapter.items = t
                }

                override fun onError(e: Throwable) {
                    hideLoading()
                }
            })
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }
}
