package com.maryang.fastrxjava.ui.user

import android.content.Context
import android.os.Bundle
import com.maryang.fastrxjava.base.BaseActivity
import com.maryang.fastrxjava.entity.GithubRepo
import org.jetbrains.anko.intentFor


class UserActivity : BaseActivity() {

    companion object {
        private const val KEY_USER = "KEY_USER"

        fun start(context: Context, user: GithubRepo.GithubRepoUser) {
            context.startActivity(
                context.intentFor<UserActivity>(
                    KEY_USER to user
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_user)
        intent.getParcelableExtra<GithubRepo.GithubRepoUser>(KEY_USER).let {
            supportActionBar?.run {
                title = it.userName
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}
