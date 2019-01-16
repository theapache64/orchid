package com.tp.orchid.ui.activities.main

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.tp.orchid.R
import com.tp.orchid.data.remote.ApiInterface
import com.tp.orchid.data.remote.login.LogInRequest
import com.tp.orchid.data.remote.login.LogInResponse
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            apiInterface.logIn(
                LogInRequest("shifar", "shifar123")
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : SingleObserver<LogInResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onSuccess(t: LogInResponse) {
                        Snackbar.make(view, t.message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                })


        }
    }
}
