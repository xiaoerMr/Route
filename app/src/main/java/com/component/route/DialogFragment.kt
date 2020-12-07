package com.component.route

import android.view.View
import com.component.module_basis.base.BaseLazyFragment
import com.component.module_basis.dialog.DialogBottomGridItem
import com.component.module_basis.dialog.DialogHandle
import com.component.module_basis.dialog.DialogInputType
import com.component.module_basis.loge
import com.component.module_basis.toast
import kotlinx.android.synthetic.main.fragment_dialog.view.*

class DialogFragment : BaseLazyFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_dialog

    override fun initView(view: View) {
        loge("--Fragment1-onCreateView-initView---")
        setDialog(view)
    }

    override fun initData() {
        loge("--Fragment1-initData---")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        loge("--Fragment1-onHiddenChanged-${hidden}--")
    }

    override fun onPause() {
        super.onPause()
        loge("--Fragment1-onPause---")
    }

    override fun onStop() {
        super.onStop()
        loge("--Fragment1-onStop---")
    }

    private fun setDialog(view: View) {
        val listItem = listOf("第一", "第二", "第三", "第四", "第五", "第六", "第七", "第八", "第九")

        view.button.setOnClickListener {
            DialogHandle.basic(
                mContext,
                "基础弹窗",
                "我说消息弹窗内容",
                { mContext.toast("点击了确定按钮") })
        }

        view.button2.setOnClickListener {
            DialogHandle.singleChoice(
                mContext,
                "单选弹窗",
                listItem,
                { _, _, item -> mContext.toast("您喜欢的是： $item") },
                2,
                "请选择您喜欢的选项"
            )
        }

        view.button3.setOnClickListener {
            DialogHandle.multiChoice(
                mContext,
                "多选弹窗",
                listItem,
                { _, _, item -> mContext.toast("您喜欢的是： $item") },
                intArrayOf(4, 5),
                "请选择您喜欢的选项"
            )
        }
        view.button4.setOnClickListener {
            DialogHandle.inputSmallText(
                mContext,
                "密码",
                DialogInputType.dialogInputTypeText,
                { _, text -> mContext.toast("您输入的是： $text") },
                "请输入密码",
                6,
                "6位密码，只能包含数字和字母"
            )
        }

        view.button5.setOnClickListener {
            DialogHandle.datePicker(
                mContext,
                "日期",
                { _, data -> mContext.toast("您选择的是： $data") },
                "请选择日期"
            )
        }

        val list = mutableListOf(
            DialogBottomGridItem(R.drawable.ic_dialog, "菜单1"),
            DialogBottomGridItem(R.drawable.ic_dialog, "菜单2"),
            DialogBottomGridItem(R.drawable.ic_dialog, "菜单3"),
            DialogBottomGridItem(R.drawable.ic_dialog, "菜单4"),
            DialogBottomGridItem(R.drawable.ic_dialog, "菜单5"),
        )

        view.button6.setOnClickListener {
            DialogHandle.basicGridBottom(
                mContext,
                "底部菜单",
                list,
                { _, index, _ -> mContext.toast("您选择的是第：${index + 1} 个菜单") },
            )
        }
    }

}