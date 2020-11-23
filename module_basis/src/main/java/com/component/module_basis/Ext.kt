package com.component.module_basis

import android.content.Context
import android.widget.Toast

fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}