package com.maryang.fastrxjava.ui.repos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.base.BaseActivity
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.util.BackpressureSample
import com.maryang.fastrxjava.event.DataObserver
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_github_repos.*


class GithubReposActivity : BaseActivity() {

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

        refreshLayout.setOnRefreshListener { viewModel.searchGithubRepos() }

        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                viewModel.searchGithubRepos(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        subscribeSearch()
        subscribeDataObserver()
    }

    private fun subscribeSearch() {
        viewModel.searchGithubReposSubject()
            .doOnNext {
                if (it) showLoading()
            }
            //.flatMap { viewModel.searchGithubReposObservable() } // 검색을 쉬다 보면 다른 검색이 나왔다 다시 사라진다.
            .switchMap { viewModel.searchGithubReposObservable() } // -> 개선
            // 반환 타임에 들어온애가 있으면 반환하지 않는다?
            // api가 종료되고 ui 업데이트를 하기전에 또 api가 날아가서 ui업데이틀 시켜 잠시 깜빡이는 현상 발생
            //들어온 순서에 따라서 마지막애 들어온 애를 업데이트 한다.

            .subscribe(object : DisposableObserver<List<GithubRepo>>() {
                override fun onNext(t: List<GithubRepo>) {
                    hideLoading()
                    adapter.items = t
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    hideLoading()
                }
            })
    }

    private fun subscribeDataObserver() {
        DataObserver.observe()
            .filter { it is GithubRepo }
            .subscribe { repo ->
                adapter.items.find {
                    it.id == repo.id
                }?.apply {
                    star = star.not()
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }
}
