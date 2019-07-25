package com.maryang.fastrxjava.ui.my_page

import android.os.Bundle
import com.maryang.fastrxjava.base.BaseActivity

class MyPageActivity : BaseActivity() {

    override var isChildActivity: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(com.maryang.fastrxjava.R.layout.activity_my_page)

        super.onCreate(savedInstanceState)

    }
}
