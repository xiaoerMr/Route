package com.component.module_basis

import android.content.Context
import android.util.Log
import android.widget.Toast


fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.loge(msg:String){
    Log.e("TAG",msg)
}

