package com.sai.module_map.marker

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.animation.Animation
import com.amap.api.maps.model.animation.TranslateAnimation
import com.component.module_basis.loge
import com.sai.module_map.R


class MoveMarker constructor(
    private val context: Context,
    private var data: MarkerData,
    private val aMap: AMap,
) {

    private lateinit var addMarker: Marker
    private var show: Boolean = true
    private var showMarker: Boolean = true

    private val vectorBorder by lazy {
        val create = VectorDrawableCompat.create(context.resources,
            R.drawable.ic_border,
            context.theme)
        create
    }
    private val option = MarkerOptions()

    fun initMarker() {
        option.position(data.latLng)
        option.icon(getMarkerIcon())

        addMarker = aMap.addMarker(option)
    }

    fun startMove(data: MarkerData) {
        addMarker?.let {

            this.data = data
            it.setIcon(getMarkerIcon())

            val animator = TranslateAnimation(data.latLng)
            animator.setDuration(3000)
            it.setAnimation(animator)
//            it.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart() {
//
//                }
//
//                override fun onAnimationEnd() {
//                    loge("------onAnimationEnd-----")
////                    it.position = data.latLng
//                }
//            })
            it.startAnimation()
        }
    }

    fun showMarkerSignage(show: Boolean) {

        addMarker?.let {
            this.show = show
            it.setIcon(getMarkerIcon())
        }
    }

    fun showMarker(showMarker: Boolean) {
        addMarker?.isVisible = showMarker
    }

    fun deleteMarker() {
        addMarker?.remove()
    }

    private fun getMarkerIcon(): BitmapDescriptor {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_marker, null)

        val vMarkerIcon = inflate.findViewById<ImageView>(R.id.vMarkerIcon)
        val vMarkerSignage = inflate.findViewById<LinearLayout>(R.id.vMarkerSignage)

        if (show) {
            setSignageView(inflate, vMarkerSignage)
        } else {
            vMarkerSignage.visibility = View.GONE
        }

        if (data.alert) {
            setSignageView(inflate, vMarkerSignage, data.alert)
        }

        vMarkerIcon.setBackgroundResource(R.drawable.ic_airplane)
        vMarkerIcon.rotation = data.rotate

        return BitmapDescriptorFactory.fromView(inflate)
    }

    private fun setSignageView(
        inflate: View,
        vMarkerSignage: LinearLayout,
        alert: Boolean = false,
    ) {
        val vMarkerTitle = inflate.findViewById<TextView>(R.id.vMarkerTitle)
        val vMarkerHeight = inflate.findViewById<TextView>(R.id.vMarkerHeight)
        val vMarkerSpeed = inflate.findViewById<TextView>(R.id.vMarkerSpeed)
        val vMarkerRotate = inflate.findViewById<TextView>(R.id.vMarkerRotate)
        val vMarkerLat = inflate.findViewById<TextView>(R.id.vMarkerLat)
        val vMarkerLng = inflate.findViewById<TextView>(R.id.vMarkerLng)

        vMarkerSignage.visibility = View.VISIBLE

        if (alert) {
            //告警颜色框
            vectorBorder?.setTint(context.resources.getColor(R.color.yellow))
        }

        vMarkerSignage.background = vectorBorder

        vMarkerTitle.text = data.name
        vMarkerHeight.text = "高度: ${data.height}m"
        vMarkerSpeed.text = "速度: ${data.speed}km/h"
        vMarkerRotate.text = "航向角: ${data.rotate}°"
        vMarkerLat.text = "纬度: ${data.latLng.latitude}"
        vMarkerLng.text = "经度: ${data.latLng.longitude}"
    }


}