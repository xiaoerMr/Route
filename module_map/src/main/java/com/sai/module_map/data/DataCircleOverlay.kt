package com.sai.module_map.data

import com.amap.api.maps.model.LatLng

data class DataCircleOverlay(
    var name:String,
    var centerLatLng: LatLng,
    var radius:Double)