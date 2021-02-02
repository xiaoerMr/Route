package com.sai.module_map.overlay

import com.amap.api.maps.model.LatLng

data class DataOverlay(
    val name:String,
    var positions: MutableList<LatLng>)
