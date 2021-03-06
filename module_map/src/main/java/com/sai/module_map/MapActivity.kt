package com.sai.module_map

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.component.module_basis.RoutePath
import com.component.module_basis.base.BaseActivity
import com.sai.module_map.real.MapRealFragment
import kotlinx.android.synthetic.main.activity_map.*

@Route(path = RoutePath.pageMap)
class MapActivity : BaseActivity() {

    override fun getLayoutId()=R.layout.activity_map

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager
            .beginTransaction()
//            .replace(fragmentContainer.id ,MapFragment())
            .replace(fragmentContainer.id ,MapRealFragment())
            .commit()
    }

    override fun initData() {
    }
}