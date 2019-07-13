package com.maryang.fastrxjava.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.base.BaseApplication
import com.maryang.fastrxjava.data.source.ApiManager
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_github_repos.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class GithubReposActivity : AppCompatActivity() {

    private val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    val subject = PublishSubject.create<String>()   // hot observable

    fun coldHot(){
        //subject는 subscribe 여부와 상관없이 이벤트를 발행한다.
        //내부에 observer list가 잇다.
        //subscribe 하면 onNext를 싱행하는것이 아니라 list.add(observer) 이벤트 발행하고 상관없다..!
        //onNext를 하면 list.forEach{ observer.onNext() }
        //cold observable은 이벤트 발행하면 구독해요
        //subject : list를 들고있어서 onNext하면 전체를 실행한다.
        //.publish : connectableObservable : cold -> hot
        //observable.connect : 이벤트 발행

    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        refreshLayout.setOnRefreshListener { searchLoad(viewModel.searchText, false) }

        Log.d(BaseApplication.TAG, "current thread: ${Thread.currentThread().name}")




//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just thread : ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.io())
//            .subscribe()
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just computation : ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.computation())
//            .subscribe()
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just trampoline : ${Thread.currentThread().name}")
//            }//메인이지만 비동기로 만들어준다..! task화가 되어 메인한테 줄을 세우고 메인이 쉬고있을때 작업을 한다.
//            .subscribeOn(Schedulers.trampoline())
//            .subscribe({
//                Log.d(BaseApplication.TAG, "just trampoline after : ${Thread.currentThread().name}")
//            },{
//
//            })
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just newThread : ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.newThread())
//            .subscribe()


//        val thread = Executors.newSingleThreadExecutor()
//        //싱글로 만들고 스레드의 동기성을 좀 유지하고 싶을때 사용! from
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just newThread2 : ${Thread.currentThread().name}")
//            }
//            //.subscribeOn(Schedulers.newThread())
//            .subscribeOn(Schedulers.from(thread))
//            .subscribe()
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just newThread2 : ${Thread.currentThread().name}")
//            }
//            //.subscribeOn(Schedulers.newThread())
//            .subscribeOn(Schedulers.from(thread))
//            .subscribe()
//
//
//
//        //검색할 때 text 바뀔때마다 api 보내기
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just io thread: ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.io())
//            .subscribe()
//
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(
//                    BaseApplication.TAG,
//                    "just computation thread: ${Thread.currentThread().name}"
//                )
//            }
//            .subscribeOn(Schedulers.computation())
//            .subscribe()
//
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just trampoline thread: ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.trampoline())
//            .subscribe()
//
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just newThread: ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.newThread())
//            .subscribe()
//
//        Single.just(true)
//            .doOnSuccess {
//                Log.d(BaseApplication.TAG, "just newThread2: ${Thread.currentThread().name}")
//            }
//            .subscribeOn(Schedulers.newThread())
//            .subscribe()


        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
//                handler.removeCallbacksAndMessages(null)
//                handler.sendMessageDelayed(Message().apply {
//                    what = WHAT_SEARCH
//                    obj = text.toString()
//                }, 400)

                //Handler -> debounce
                subject.onNext(text.toString()) //구독하고 있는것에 이벤트를 발행시킨다.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        subjectSubscribe()


    }

    //get Error ! only main thread can handle view
    private fun subjectSubscribe() {
        subject
            .debounce (400, TimeUnit.MILLISECONDS)  //변경된 text가 여기에 걸려서 400 밀리세컨드 대기
            //-> 스레드 체인지가 발생한다.
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                searchLoad(it, true)    // onNext 다음에 발생 !
            }
    }

    private fun searchLoad(search: String, showLoading: Boolean) {
        if (showLoading)
            showLoading()

        val githubApi = ApiManager.githubApi

        val mObservable: Single<List<GithubRepo>> =
            githubApi
                .searchRepos(search).map {
                    it.asJsonObject.getAsJsonArray("items")
                        .map{
                            ApiManager.gson.fromJson(it, GithubRepo::class.java)!!
                        }
                }

        mObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.items = it
            },{

            })

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



//        viewModel.searchGithubRepos(search)
//            .toFlowable()
//            .debounce(400, TimeUnit.MILLISECONDS)
//            .subscribe(object : DisposableSingleObserver<List<GithubRepo>>() {
//                override fun onSuccess(t: List<GithubRepo>) {
//                    hideLoading()
//                    adapter.items = t
//                }
//
//                override fun onError(e: Throwable) {
//                    hideLoading()
//                }
//            })



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
//        viewModel.getGithubRepos()
//            .subscribe(object: DisposableSingleObserver<List<GithubRepo>>(){    //observer를 구독하자
//                override fun onSuccess(t: List<GithubRepo>) {
//                    hideLoading()
//                    adapter.items = t
//                }
//
//                override fun onError(e: Throwable) {
//                    hideLoading()
//                    Log.d("tagg", e.toString())
//                }
//
//            })


        //Single.zip --> 두 쓰레드를 동시에 보내고 늦게 끝난 놈 기준으로 subscribe처리를 한다.
        //map - element를 변경한다.
        //flat map 객체 자체를 변경
        //flat map을 쓰는이유는 결과물이 달라질것이다 라는 명시 !!

        //이눔시키 에러 !!!
//        viewModel.getGithubRepos().toMaybe()
//            .subscribeOn(Schedulers.io())   //해준 이유는 레포 따라 올라가면 main엣서 끝나게끔 해놨으니까 !!
//            .doOnSuccess{
//                //getGithubRepos 종료되면 아래의 로그가 불립니다.
//                Log.d(BaseApplication.TAG, "getGithubRepos")
//            }
//                // getUserRepo 호출
//            .flatMap { viewModel.getUserRepo() }
//            .doOnSuccess{
//                //getUserRepo 종료되면 로그가 불림.
//                Log.d(BaseApplication.TAG, "getUser")
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
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

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }
}
