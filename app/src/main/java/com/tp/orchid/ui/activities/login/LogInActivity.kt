package com.tp.orchid.ui.activities.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tp.orchid.R
import com.tp.orchid.databinding.ActivityLogInBinding
import com.tp.orchid.ui.activities.main.MainViewModel
import com.tp.orchid.utils.extensions.bindContentView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LogInActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        val binding = bindContentView<ActivityLogInBinding>(R.layout.activity_log_in)

        // Creating ViewModel
        val viewModel = ViewModelProviders.of(this, factory).get(LogInViewModel::class.java)

        // Binding ViewModel to LayoutBinder
        binding.viewModel = viewModel
    }


    companion object {

        const val ID = R.id.LOGIN_ACTIVITY_ID

        fun start(context: Context) {
            val intent = Intent(context, LogInActivity::class.java)
            context.startActivity(intent)
        }
    }

}
