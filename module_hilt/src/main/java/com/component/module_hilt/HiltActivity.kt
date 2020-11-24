package com.component.module_hilt

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.component.module_basis.RoutePath
import com.component.module_basis.base.BaseActivity
import com.component.module_basis.http.ApiService
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = RoutePath.pageHilt)
class HiltActivity : BaseActivity() {

//    @Inject
//    lateinit var okhttp: Retrofit



//    @Inject
//    lateinit var client: OkHttpClient

    // 获取viewModel (使用了注入方式)
    private val viewModel: HiltViewModel by lazy { ViewModelProvider(this).get(HiltViewModel::class.java) }

    override fun getLayoutId(): Int = R.layout.activity_hilt

    override fun initView(savedInstanceState: Bundle?) {
        println("-- 开始请求数据--")
        viewModel.doTest()
    }

    override fun initData() {
    }
}