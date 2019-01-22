package com.tp.orchid.ui.activities.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null

    fun showLoadingDialog(message: String) {
        hideLoadingDialog()

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(message)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)

        this.progressDialog = progressDialog
        progressDialog.show()
    }

    fun hideLoadingDialog() {
        progressDialog?.dismiss()
    }
}