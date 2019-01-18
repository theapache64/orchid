package com.tp.orchid.ui.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.data.remote.login.LogInResponse
import com.tp.orchid.databinding.ActivityMainBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.bindContentView
import com.tp.orchid.utils.extensions.debug
import com.tp.orchid.utils.extensions.info
import com.tp.orchid.utils.extensions.error
import com.tp.orchid.utils.extensions.toast
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory;

    @set:Inject
    var user: LogInResponse.User? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = bindContentView<ActivityMainBinding>(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val viewModel: MainViewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        binding.viewModel = viewModel

        // Watching for movie response
        viewModel.getSearchResponse().observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> debug("Loading...")
                Resource.Status.SUCCESS -> info("Success : ${it.data!!.search}")
                Resource.Status.ERROR -> {
                    error("Error")
                    toast(it.message!!)
                }
            }
        })
    }

    companion object {

        const val ID = R.id.MAIN_ACTIVITY_ID

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}
