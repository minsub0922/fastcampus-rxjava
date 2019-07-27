package com.maryang.fastrxjava.ui.my_page

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.maryang.fastrxjava.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_page.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable



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



        subscribeMyProfile()
        subscribeRepos()
    }

    private fun subscribeMyProfile(){
        viewModel
            .getMyProfile()
            .subscribe({myInfo ->
                myImage.background = ShapeDrawable(OvalShape())
                myImage.clipToOutline = true
                Glide.with(this)
                    .load(myInfo.profileImageUrl)
                    .into(myImage)
                myName.text = myInfo.name
                introduce.text = myInfo.introduce
            },{

            })

    }
    private fun subscribeRepos(){
        viewModel
            .getMyRepos()
            .subscribe(
                {
                    adapter.items = it
                },{
                }
            )
    }
}
