package com.component.route

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.component.module_basis.base.BaseActivity

class MotionActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_motion

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }

    companion object{
        fun jumpDialogActivity(activity: AppCompatActivity){
            activity.startActivity(Intent(activity, MotionActivity::class.java))
        }
    }
}