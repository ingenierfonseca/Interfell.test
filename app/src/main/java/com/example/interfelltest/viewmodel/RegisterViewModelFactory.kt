package com.example.interfelltest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interfelltest.view.IRegisterResultCallBack

class RegisterViewModelFactory(private val context: Context, val listenerActivity: IRegisterResultCallBack): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(context, listenerActivity) as T
    }
}