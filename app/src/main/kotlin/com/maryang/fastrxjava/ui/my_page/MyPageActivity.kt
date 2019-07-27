package com.maryang.fastrxjava.ui.my_page

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPageActivity : BaseActivity() {

    override var isChildActivity: Boolean = true

    private val viewModel: GithubMyReposViewModel by lazy {
        GithubMyReposViewModel()
    }
    private val adapter: GithubMyRepoAdapter by lazy {
        GithubMyRepoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(com.maryang.fastrxjava.R.layout.activity_my_page)

        super.onCreate(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        subscribeRepos()
    }

    private fun subscribeRepos(){
        viewModel
            .getMyInfo()
            .subscribe(
                {
                    adapter.items = it
                },{
                    Log.d("tagg",it.message)
                }
            )
    }
}
