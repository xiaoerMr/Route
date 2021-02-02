package com.sai.module_map.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.sai.module_map.R
import com.sai.module_map.data.DataMarker

class OverlayMarker(private val aMap: AMap, private val context: Context) {
    private lateinit var marker: Marker

    private val optionMarker by lazy { MarkerOptions().anchor(0.5f,0.5f) }

    private fun getMarkerIcon(data: DataMarker): BitmapDescriptor {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_overlay_icon, null)
//        val overlayIcon = inflate.findViewById<ImageView>(R.id.overlayIcon)
        val overlayIconTitle = inflate.findViewById<TextView>(R.id.overlayIconTitle)
        overlayIconTitle.text = data.name
        return BitmapDescriptorFactory.fromView(inflate)
    }

    fun createOverlay(data: DataMarker) {

        optionMarker.position(data.latLng).icon(getMarkerIcon(data))
        marker = aMap.addMarker(optionMarker)
    }
    fun updateOverlay(data: DataMarker) {
        marker?.let {
            it.position = data.latLng
            it.setIcon(getMarkerIcon(data))
        }
    }
    fun showOverlay(show: Boolean) {
        marker?.let {
            it.isVisible = show
        }
    }

    fun deleteOverlay() {
        marker?.let {
            it.remove()
        }
    }
}