package com.maryang.fastrxjava.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.R
import com.maryang.fastrxjava.base.BaseApplication
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
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
        setContentView(R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        refreshLayout.setOnRefreshListener { load() }

        load(true)
    }

    private fun load(showLoading: Boolean = false) {
        if (showLoading)
            showLoading()
//        viewModel.getGithubRepos(
//            {
//                hideLoading()
//                adapter.items = it
//            },
//            {
//                hideLoading()
//            }
//        )


        //백버튼을 누르면 액티비티는 종료가됩니다.
        //액티비티에 있느 Obervable이 구독하는 Observer는 액티비티의 Context를 참조합니다(view)  -> GC가 쑤집을 못한다! 참조하고 있으니까
        //액티비티는 종료가 되이ㅓ야하는데, Dispose 가 되어있지않은 Observer에 Context가 잡혀있어 사라지지 않는다.
        viewModel.getGithubRepos()
            .subscribe(object: DisposableSingleObserver<List<GithubRepo>>(){    //observer를 구독하자
                override fun onSuccess(t: List<GithubRepo>) {
                    hideLoading()
                    adapter.items = t
                }

                override fun onError(e: Throwable) {
                    hideLoading()
                }

            })


        //Single.zip --> 두 쓰레드를 동시에 보내고 늦게 끝난 놈 기준으로 subscribe처리를 한다.
        //map - element를 변경한다.
        //flat map 객체 자체를 변경
        //flat map을 쓰는이유는 결과물이 달라질것이다 라는 명시 !!

        viewModel.getGithubRepos().toMaybe()
            .subscribeOn(Schedulers.io())   //해준 이유는 레포 따라 올라가면 main엣서 끝나게끔 해놨으니까 !!
            .doOnSuccess{
                //getGithubRepos 종료되면 아래의 로그가 불립니다.
                Log.d(BaseApplication.TAG, "getGithubRepos")
            }
                // getUserRepo 호출
            .flatMap { viewModel.getUserRepo() }
            .doOnSuccess{
                //getUserRepo 종료되면 로그가 불림.
                Log.d(BaseApplication.TAG, "getUser")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun load2(){
        //Observable이 이벤틀르 발행하면
        viewModel.getUserRepo()
            .subscribe(object: DisposableMaybeObserver<User>(){
            //Observer
                override fun onSuccess(t: User) {// t != null !!!! null이 아닌것만 온다.
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onComplete() { // null이 발생했을때
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
    }

    private fun load3(){

        viewModel.updatetUser()
            .subscribe(object: DisposableCompletableObserver(){
                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
