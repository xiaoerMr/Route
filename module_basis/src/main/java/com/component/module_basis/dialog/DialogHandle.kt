package com.component.module_basis.dialog

import android.content.Context
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.GridItemListener
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.datetime.DateTimeCallback
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.datetime.timePicker
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.MultiChoiceListener
import com.afollestad.materialdialogs.list.SingleChoiceListener
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.component.module_basis.R

object DialogHandle {

    private val dialogIcon = R.drawable.ic_dialog
    private lateinit var loadingDialog: MaterialDialog

    fun loadingShow(windowContext: Context, cancelOutside: Boolean = false) {
        loadingDialog = MaterialDialog(windowContext)
            .cancelOnTouchOutside(cancelOutside)
            .customView(R.layout.view_dialog_loading)
    }

    fun loadingDismiss() {
        loadingDialog?.let { it.dismiss() }
    }

    /**
     * 标准的dialog 确认取消按钮，标题，内容，确认回调
     */
    fun basic(
        windowContext: Context,
        title: String,
        msg: String,
        okCallback: DialogCallback,
        cancelOutside: Boolean = false
    ) {
        MaterialDialog(windowContext)
            .icon(dialogIcon)
            .cancelOnTouchOutside(cancelOutside)
            .negativeButton()
            .positiveButton(click = okCallback)
            .show {
                title(text = title)
                message(text = msg)
            }
    }

    /**
     * 单选弹窗
     *  title：标题
     *  msg： 消息
     *  defSelection： 默认选项
     *  listItem：数据源
     *  singleChoiceListener： 选择回调
     *  cancelOutside：外部点击关闭弹窗
     */
    fun singleChoice(
        windowContext: Context,
        title: String,
        listItem: List<CharSequence>,
        singleChoiceListener: SingleChoiceListener,
        defSelection: Int = 0,
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)
            .cancelOnTouchOutside(cancelOutside)
            .icon(dialogIcon)
            .negativeButton()
            .positiveButton()
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                listItemsSingleChoice(
                    items = listItem,
                    initialSelection = defSelection,
                    selection = singleChoiceListener
                )
            }
    }

    /**
     * 多选
     *  listItems 数据源
     *  multiChoiceListener 选择回调
     *  defSelection 默认选项
     */
    fun multiChoice(
        windowContext: Context,
        title: String,
        listItems: List<String>,
        multiChoiceListener: MultiChoiceListener,
        defSelection: IntArray = intArrayOf(0, 1),
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)
            .cancelOnTouchOutside(cancelOutside)
            .icon(dialogIcon)
            .negativeButton()
            .positiveButton()
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                listItemsMultiChoice(
                    items = listItems,
                    initialSelection = defSelection,
                    selection = multiChoiceListener
                )
            }
    }

    /**
     * 底部网格弹窗
     * listItem 数据源（BasicGridItem： 每一项包括图片和标题）
     * gridItemListener 选择回调
     */
    fun basicGridBottom(
        windowContext: Context,
        title: String,
        listItem: List<DialogBottomGridItem>,
        gridItemListener: GridItemListener<DialogBottomGridItem>,
        msg: String? = null
    ) {
        MaterialDialog(windowContext, BottomSheet())
            .icon(dialogIcon)
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                gridItems(listItem, selection = gridItemListener)
            }
    }

    /**
     * 输入较短的文本
     *  inputType 输入类型 ：dialogInputTypeNumber 和 dialogInputTypeText
     *  inputCallback 输入回调
     *  maxSize 输入最大字节数
     *  hint 输入提示
     *
     */
    fun inputSmallText(
        windowContext: Context,
        title: String,
        inputType: Int = DialogInputType.dialogInputTypeText,
        inputCallback: InputCallback,
        hint: String? = null,
        maxSize: Int? = null,
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)//LayoutMode.WRAP_CONTENT
            .cancelOnTouchOutside(cancelOutside)
            .icon(dialogIcon)
            .negativeButton()
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                input(
                    inputType = inputType,
                    hint = hint,
                    maxLength = maxSize,
                    waitForPositiveButton = true,// 是否关闭监听输入过程的回调
                    allowEmpty = false,//不允许输入空
                    callback = inputCallback
                )
            }
    }

    /**
     *  日期和时间选择框
     *  dateTimeCallback 选择回调
     */
    fun dataTime(
        windowContext: Context,
        title: String,
        dateTimeCallback: DateTimeCallback,
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)//LayoutMode.WRAP_CONTENT
            .icon(dialogIcon)
            .cancelOnTouchOutside(cancelOutside)
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                dateTimePicker(
                    requireFutureDateTime = false,
                    show24HoursView = false,
                    dateTimeCallback = dateTimeCallback
                )
            }
    }

    /**
     *  时间选择框
     *  timeCallback 选择回调
     */
    fun timePicker(
        windowContext: Context,
        title: String,
        timeCallback: DateTimeCallback,
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)//LayoutMode.WRAP_CONTENT
            .icon(dialogIcon)
            .cancelOnTouchOutside(cancelOutside)
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                timePicker(
                    show24HoursView = false,
                    timeCallback = timeCallback
                )
            }
    }

    /**
     *  日期选择框
     *  dateCallback 选择回调
     */
    fun datePicker(
        windowContext: Context,
        title: String,
        dateCallback: DateTimeCallback,
        msg: String? = null,
        cancelOutside: Boolean = false,
    ) {
        MaterialDialog(windowContext)//LayoutMode.WRAP_CONTENT
            .icon(dialogIcon)
            .cancelOnTouchOutside(cancelOutside)
            .show {
                title(text = title)
                msg?.let {
                    message(text = msg)
                }
                datePicker(
                    dateCallback = dateCallback
                )
            }
    }
}