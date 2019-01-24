package com.tp.orchid.ui.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.databinding.ActivityLogInBinding
import com.tp.orchid.ui.activities.base.BaseAppCompatActivity
import com.tp.orchid.ui.activities.main.MainActivity
import com.tp.orchid.utils.Resource
import com.tp.orchid.utils.extensions.*
import dagger.android.AndroidInjection
import javax.inject.Inject

class LogInActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        // Binding
        val binding = bindContentView<ActivityLogInBinding>(R.layout.activity_log_in)

        // Creating ViewModel
        val viewModel = ViewModelProviders.of(this, factory).get(LogInViewModel::class.java)

        // Binding ViewModel to LayoutBinder
        binding.viewModel = viewModel

        viewModel.getLogInLiveData().observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showLoadingDialog("Logging in...")
                }
                Resource.Status.ERROR -> {
                    hideLoadingDialog()
                    toast(it.message!!)
                }
                Resource.Status.SUCCESS -> {
                    hideLoadingDialog()
                    toast("Logged in!")
                    startActivity(MainActivity.getStartIntent(this))
                    finish()
                }
            }
        })
    }


    companion object {

        const val ID = R.id.LOGIN_ACTIVITY_ID
        fun getStartIntent(context: Context): Intent = Intent(context, LogInActivity::class.java)
    }

}
