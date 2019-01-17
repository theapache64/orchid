package com.tp.orchid.ui.activities.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tp.orchid.R

class LogInActivity : AppCompatActivity() {

    companion object {

        const val ID = R.id.LOGIN_ACTIVITY_ID

        fun start(context: Context) {
            val intent = Intent(context, LogInActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }
}
