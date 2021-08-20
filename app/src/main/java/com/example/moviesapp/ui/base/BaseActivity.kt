package com.example.moviesapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.moviesapp.App
import com.example.moviesapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel>() :
    AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    private lateinit var viewModel: VM

    abstract fun getViewModel(): VM

    private fun initViewModel() {
        this.viewModel = getViewModel()
    }

    //use open keyword to allow child class to override it
    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject()
        initViewModel()
        super.onCreate(savedInstanceState)

        viewModel.setServiceKey(resources.getString(R.string.api_key))
        binding.executePendingBindings()
    }

    fun getApplicationComponent() = (application as App).getAppComponent()


    fun showError(message: String) {
        val snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snack.view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        snack.show()
    }

    fun showSuccess(message: String) {
        val snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snack.view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        snack.show()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // layout Behind nav bar
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // layout Behind status bar
                    )
        }
    }
}