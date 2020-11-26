package com.component.module_basis.dialog

import android.text.InputType

object DialogInputType {
    const val dialogInputTypeNumber =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    const val dialogInputTypeText =
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

}