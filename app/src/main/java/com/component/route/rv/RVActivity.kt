package com.component.route.rv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.component.module_basis.base.BaseActivity
import com.component.route.R
import kotlinx.android.synthetic.main.activity_rv.*

class RVActivity : BaseActivity() {


    private val adapter by lazy { RVAdapter() }

    companion object {
        fun jumpDialogActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, RVActivity::class.java))
        }
    }

    override fun getLayoutId() = R.layout.activity_rv

    override fun initView(savedInstanceState: Bundle?) {
        vRV.layoutManager = LinearLayoutManager(this)
        vRV.adapter = adapter
        vRV.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        vRV.startLayoutAnimation()
    }

    override fun initData() {
        val data = mutableListOf<RVData>()
        for (i in 1..31) {
            data.add(RVData("NO.$i", i))
        }
        adapter.setNewData(data)
    }
}