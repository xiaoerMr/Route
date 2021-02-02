package com.sai.module_map.overlay

import com.sai.module_map.data.DataOverlay

abstract class OverlayLayout {

    abstract fun createOverlay(data: DataOverlay)
    abstract fun updateOverlay(data: DataOverlay)
    abstract fun showOverlay(show: Boolean)
    abstract fun deleteOverlay()
}