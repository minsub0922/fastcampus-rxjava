package com.maryang.fastrxjava.data.source

import retrofit2.Call
import retrofit2.Callback

abstract class DefaultCallback<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
    }
}
