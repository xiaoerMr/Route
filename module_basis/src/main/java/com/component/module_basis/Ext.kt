package com.component.module_basis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast


fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

