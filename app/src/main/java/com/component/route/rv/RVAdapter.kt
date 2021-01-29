package com.component.route.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.component.route.R

class RVAdapter : RecyclerView.Adapter<RVViewHolder>() {

   private var data: MutableList<RVData>? = null

    fun setNewData(data: MutableList<RVData>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setAddDatas(data: MutableList<RVData>) {
        if (this.data == null) {
            this.data = data
        } else {
            this.data!!.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setAddDatas(data: RVData) {
        if (this.data == null) {
            this.data = mutableListOf()
        }
        this.data!!.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false);
        return RVViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.vItemTitle.text = data!![position].name
        holder.vItemName.text = "this is No.${data!![position].age}"
    }

    override fun getItemCount(): Int {
        data?.let { return it.size }
        return 0
    }
}