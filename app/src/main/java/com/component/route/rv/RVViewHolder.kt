package com.component.route.rv

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.component.route.R

class RVViewHolder(view : View): RecyclerView.ViewHolder(view) {

    val vItemTitle:TextView = view.findViewById(R.id.vItemTitle)
    val vItemName:TextView = view.findViewById(R.id.vItemName)
}