package com.maryang.fastrxjava.ui.my_page

import android.os.Bundle
import android.util.Log
import com.maryang.fastrxjava.base.BaseActivity

class MyPageActivity : BaseActivity() {

    override var isChildActivity: Boolean = true

    private val viewModel: GithubMyProfileViewModel by lazy {
        GithubMyProfileViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(com.maryang.fastrxjava.R.layout.activity_my_page)

        super.onCreate(savedInstanceState)

        viewModel
            .getMyInfo()
            .subscribe(
                {
                    Log.d("tagg",it.toString())
                },{
                    Log.d("tagg",it.message)
                }
            )

    }
}
