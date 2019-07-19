package com.maryang.fastrxjava.ui.repo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import com.maryang.fastrxjava.util.DataObserver
import kotlinx.android.synthetic.main.activity_github_repo.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk21.listeners.onClick
import java.util.*


class GithubRepoActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ID = "KEY_ID"

        fun start(context: Context, id: Long) {
            context.startActivity(
                context.intentFor<GithubRepoActivity>(
                    KEY_ID to id
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_github_repo)

        star.onClick {
            DataObserver.post(
                GithubRepo(
                    intent.getLongExtra(KEY_ID, 0),
                    "name",
                    false,
                    User(
                        0,
                        "userName",
                        "name",
                        "email",
                        "company",
                        "location",
                        "bio",
                        "avatarUrl",
                        "type",
                        Date(), Date()
                    ),
                    "url",
                    "description",
                    0, 0, 0, 0,
                    Date(), Date(), Date(),
                    true
                )
            )
        }
    }
}
