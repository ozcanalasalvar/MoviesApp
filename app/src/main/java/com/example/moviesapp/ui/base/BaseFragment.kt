package com.example.moviesapp.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.moviesapp.App
import com.example.moviesapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: DB

    var mContext: Context? = null

    lateinit var mViewModel: VM

    abstract fun getViewModel(): VM

    private fun initViewModel() {
        this.mViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doBinding()
    }

    abstract fun init()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInject()
        initViewModel()
        mContext = context
    }

    private fun doBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
//        binding.executePendingBindings()
        mViewModel.setServiceKey(resources.getString(R.string.api_key))
        init()
    }

    open fun onInject() {}

    fun getApplicationComponent() = App.INSTANCE.getAppComponent()


    private var visibility = false //Defines fragment is visible to user for viewpager

    /**
     * Detect user visible hint
     */
    open fun getVisibleToUser(isVisible: Boolean) {}

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        visibility = menuVisible
    }

    fun isVisibleToUser(): Boolean = visibility

    override fun onResume() {
        super.onResume()
        getVisibleToUser(isAdded && visibility)
    }


    fun showError(message: String) {
        if ((context as Activity) is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>
            activity.showError(message)
        }
    }

    fun showSuccess(message: String) {
        if ((context as Activity) is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>
            activity.showSuccess(message)
        }
    }

}