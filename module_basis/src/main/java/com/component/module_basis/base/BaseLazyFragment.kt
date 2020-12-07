package com.component.module_basis.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.component.module_basis.loge

abstract class BaseLazyFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as AppCompatActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mContext.loge("onHiddenChanged: Fragment hidden is $hidden")
    }
    //        mActivity.supportFragmentManager
//            .beginTransaction()
//            .setMaxLifecycle(this, Lifecycle.State.RESUMED)
//            .replace(fragmentContainer.id,DialogFragment())
//            .commit()


    abstract fun getLayoutId(): Int

    abstract fun initView(view: View)

    abstract fun initData()
}
