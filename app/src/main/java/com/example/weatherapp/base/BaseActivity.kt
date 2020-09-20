package com.example.weatherapp.view.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection

/*
* BaseClass is responsible for activity binding and dependency inject ina ll the classes inheriting from it.
*/
abstract class BaseActivity <T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    var viewDataBinding: T? = null
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int
    abstract val viewModel: V
    private var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDependencyInjection()
        performDataBinding()
    }


    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding?.setVariable(bindingVariable, mViewModel)
        viewDataBinding?.executePendingBindings()
    }

    fun checkForInternet():Boolean{
        val cm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        } else {
            null
        }
        return cm?.let { it.activeNetworkInfo != null && it.activeNetworkInfo?.isConnected==true } ?: true
    }
}
