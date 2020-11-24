package com.component.module_hilt

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class HiltViewModel @ViewModelInject constructor( val repository: HiltRepository) : ViewModel() {

    fun doTest(){
        println("-- 请求数据--")
        repository.test()
    }
}