package com.sai.module_map.data

import com.amap.api.maps.model.LatLng

data class DataMarker(
    var latLng: LatLng,
    val type: Int,
    var alert: Boolean,
    val name: String,
    var speed: Float,
    var height: Float,
    var rotate: Float
)
