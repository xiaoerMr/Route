package com.sai.module_map.overlay

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.Polygon
import com.amap.api.maps.model.PolygonOptions
import com.sai.module_map.R

class OverlayPolygon (private val aMap: AMap, private val context: Context):OverlayLayout() {

    private lateinit var polygon: Polygon
    private lateinit var marker: Marker

    private val optionPolygon by lazy {
        PolygonOptions().strokeWidth(5f)
            .strokeColor(context.resources.getColor(R.color.overlayPolygonStrokeColor))
            .fillColor(context.resources.getColor(R.color.overlayPolygonFillColor))
    }
    private val optionMarker by lazy { MarkerOptions().anchor(0.5f,0.5f) }


    private fun getMarkerIcon(data: DataOverlay): BitmapDescriptor {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_overlay_icon, null)
//        val overlayIcon = inflate.findViewById<ImageView>(R.id.overlayIcon)
        val overlayIconTitle = inflate.findViewById<TextView>(R.id.overlayIconTitle)
        overlayIconTitle.text = data.name
        return BitmapDescriptorFactory.fromView(inflate)
    }

    private fun drawOverlay(data: DataOverlay) {
        if (data.positions.size > 2) {
            optionPolygon.addAll(data.positions)
            polygon = aMap.addPolygon(optionPolygon)
        }

        optionMarker.position(data.positions[0]).icon(getMarkerIcon(data))
        marker = aMap.addMarker(optionMarker)
    }
    override fun createOverlay(data: DataOverlay) {
        if (data.positions.size < 1) return
        drawOverlay(data)
    }

    override fun updateOverlay(data: DataOverlay) {
        if (data.positions.size < 1) return

        marker?.let {
            polygon.points = data.positions

            it.position = data.positions[0]
            it.setIcon(getMarkerIcon(data))
        }
    }

    override fun showOverlay(show: Boolean) {
        polygon?.let {
            it.isVisible = show
        }
        marker?.let {
            it.isVisible = show
        }
    }

    override fun deleteOverlay() {
        polygon?.let {
            it.remove()
        }
        marker?.let {
            it.remove()
        }
    }
}