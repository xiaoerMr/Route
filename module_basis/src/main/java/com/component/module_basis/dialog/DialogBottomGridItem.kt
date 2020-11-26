package com.component.module_basis.dialog

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.bottomsheets.GridItem

data class DialogBottomGridItem(
    @DrawableRes val iconRes: Int,
    override val title: String
) : GridItem {
    override fun populateIcon(imageView: ImageView) {
        imageView.setImageResource(iconRes)
    }
}
