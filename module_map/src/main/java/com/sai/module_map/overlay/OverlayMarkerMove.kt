package com.sai.module_map.overlay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.model.*
import com.amap.api.maps.model.animation.TranslateAnimation
import com.sai.module_map.R
import com.sai.module_map.data.DataMarker


class OverlayMarkerMove constructor(
    private val context: Context,
    private val aMap: AMap,
) {

    private lateinit var dataMarker: DataMarker
    private lateinit var addMarker: Marker
    private var show: Boolean = true

    private val vectorBorder by lazy {
        val create = VectorDrawableCompat.create(context.resources,
            R.drawable.ic_border,
            context.theme)

        create
    }
    private val option by lazy { MarkerOptions().anchor(0.1f, 0.5f) }

    fun initMarker(dataMarker: DataMarker) {

        this.dataMarker = dataMarker

        option.position(dataMarker.latLng)
        option.icon(getMarkerIcon())

        addMarker = aMap.addMarker(option)
    }

    fun startMove(dataMarker: DataMarker) {
        addMarker?.let {

            this.dataMarker = dataMarker
            it.setIcon(getMarkerIcon())

            val animator = TranslateAnimation(dataMarker.latLng)
            animator.setDuration(3020)
            it.setAnimation(animator)
            it.startAnimation()
        }
    }

    fun showMarkerSignage(show: Boolean) {

        addMarker?.let {
            this.show = show
            if (show){
               if ( it.anchorU != 0.1f) {
                   it.setAnchor(0.1f, 0.5f)
               }
            }else{
                if ( it.anchorU != 0.5f) {
                    it.setAnchor(0.5f, 0.5f)
                }
            }
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
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_marker_icon, null)

        val vMarkerIcon = inflate.findViewById<ImageView>(R.id.vMarkerIcon)
        val vMarkerSignage = inflate.findViewById<LinearLayout>(R.id.vMarkerSignage)

        if (show) {
            setSignageView(inflate, vMarkerSignage)
        } else {
            vMarkerSignage.visibility = View.GONE
        }

        if (dataMarker.alert) {
            setSignageView(inflate, vMarkerSignage, dataMarker.alert)
        }

        vMarkerIcon.setBackgroundResource(R.drawable.ic_airplane)
        vMarkerIcon.rotation = dataMarker.rotate

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

        vMarkerTitle.text = dataMarker.name
        vMarkerHeight.text = "高度: ${dataMarker.height}m"
        vMarkerSpeed.text = "速度: ${dataMarker.speed}km/h"
        vMarkerRotate.text = "航向角: ${dataMarker.rotate}°"
        vMarkerLat.text = "纬度: ${dataMarker.latLng.latitude}"
        vMarkerLng.text = "经度: ${dataMarker.latLng.longitude}"
    }


}