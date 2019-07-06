package com.maryang.fastrxjava.ui

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.base.BaseApplication
import com.maryang.fastrxjava.entity.GithubRepo
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_github_repos.*


class GithubReposActivity : AppCompatActivity() {

    private val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        refreshLayout.setOnRefreshListener { searchLoad(viewModel.searchText, false) }

        Log.d(BaseApplication.TAG, "current thread: ${Thread.currentThread().name}")

        searchText.addTextChangedListener(object : TextWatcher {

            private val WHAT_SEARCH = 0
            private val handler = Handler(Handler.Callback { message ->
                when (message.what) {
                    WHAT_SEARCH ->
                        searchLoad(message.obj.toString(), true)
                }
                false
            })

            override fun afterTextChanged(text: Editable?) {
                handler.removeCallbacksAndMessages(null)
                handler.sendMessageDelayed(Message().apply {
                    what = WHAT_SEARCH
                    obj = text.toString()
                }, 400)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
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
