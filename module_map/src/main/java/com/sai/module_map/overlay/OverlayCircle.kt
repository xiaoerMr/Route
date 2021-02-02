package com.sai.module_map.overlay

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.Circle
import com.amap.api.maps.model.CircleOptions
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.sai.module_map.R

class OverlayCircle(private val aMap: AMap, private val context: Context) {
    private lateinit var marker: Marker
    private lateinit var circle: Circle

    private val optionCircle by lazy {
        CircleOptions()
            .fillColor(context.resources.getColor(R.color.overlayCircleFillColor))
            .strokeColor(context.resources.getColor(R.color.overlayCircleStrokeColor))
            .strokeWidth(5f)
    }
    private val optionMarker by lazy { MarkerOptions().anchor(0.5f,0.5f) }


    private fun getMarkerIcon(data: DataCircleOverlay): BitmapDescriptor {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_overlay_icon, null)
//        val overlayIcon = inflate.findViewById<ImageView>(R.id.overlayIcon)
        val overlayIconTitle = inflate.findViewById<TextView>(R.id.overlayIconTitle)
        overlayIconTitle.text = data.name
        return BitmapDescriptorFactory.fromView(inflate)
    }

    fun createOverlay(data: DataCircleOverlay) {
        optionCircle.radius(data.radius).center(data.centerLatLng)
        circle = aMap.addCircle(optionCircle)

        optionMarker.position(data.centerLatLng).icon(getMarkerIcon(data))
        marker = aMap.addMarker(optionMarker)
    }

    fun updateOverlay(data: DataCircleOverlay) {
        circle?.let {
            it.center =data.centerLatLng
            it.radius = data.radius

            marker.position = data.centerLatLng
            marker.setIcon(getMarkerIcon(data))
        }
    }

    fun showOverlay(show: Boolean) {
        circle?.let {
            it.isVisible = show
            marker.isVisible =show
        }
    }

    fun deleteOverlay() {
        circle?.let {
            it.remove()
            marker.remove()
        }
    }
}