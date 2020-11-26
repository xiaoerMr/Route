package com.component.route

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.component.module_basis.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : BaseActivity() {

    companion object{
        fun jumpDialogActivity(activity: AppCompatActivity){
            activity.startActivity(Intent(activity, DialogActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_dialog

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id,DialogFragment())
            .commit()
    }

    override fun initData() {
    }

}