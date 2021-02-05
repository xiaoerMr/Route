package com.sai.module_map

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.amap.api.maps.model.LatLng
import com.component.module_basis.base.BaseFragment
import com.sai.module_map.data.*
import com.sai.module_map.overlay.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapFragment : BaseFragment() {

    private val mAMap by lazy { vMapView.map }
    private val random by lazy { Random(200) }
    private val latlngs: MutableList<LatLng> by lazy {
        val list = mutableListOf<LatLng>()

        list.add(LatLng(39.970273, 116.358593))
        list.add(LatLng(39.939745, 116.258343))
        list.add(LatLng(39.896561, 116.388806))
        list.add(LatLng(39.935533, 116.462963))
        list
    }
    private val dataMarkers: MutableList<DataMarker> by lazy {
        val list = mutableListOf<DataMarker>()
        for (i in 0..3) {
            val markerData = DataMarker(
                latlngs[i], 2, false, "No.$i",
                random.nextFloat(), random.nextFloat(), random.nextFloat())
            list.add(markerData)
        }
        list
    }
    private val markers: MutableMap<String, OverlayMarkerMove> by lazy {
        mutableMapOf()
    }
    private lateinit var launch: Job
    private var show = false
    private var move = true

    private lateinit var line: OverlayLine
    private lateinit var polygon: OverlayPolygon

    override fun getLayoutId() = R.layout.fragment_map

    override fun initView(view: View, savedInstanceState: Bundle?) {

        vMapView.onCreate(savedInstanceState)

        setMapUI()

        vMapMarkerShow.setOnClickListener {

            showMarker(show)
            show = !show
        }

        vMapMarkerStartMove.setOnClickListener {

            if (move) initFlowable()
            else launch.cancel()

            move = !move
        }


        var isShowLine = false
        line = OverlayLine(mAMap, mContext)
        line.createOverlay(DataOverlay("TestLine", latlngs))
        vMapLine.setOnClickListener {
            line.showOverlay(isShowLine)
            isShowLine = !isShowLine
        }

        var isShowPolygon = false
        polygon = OverlayPolygon(mAMap, mContext)
        polygon.createOverlay(DataOverlay("TestPolygon", latlngs))
        vMapPolygon.setOnClickListener {
            polygon.showOverlay(isShowPolygon)
            isShowPolygon = !isShowPolygon
        }

        var isShowCircle = false
        var circle = DataCircleOverlay("TextCircle", latlngs[2], 1000.0)
        val overlayCircle = OverlayCircle(mAMap, mContext)
        overlayCircle.createOverlay(circle)
        vMapCircle.setOnClickListener {
            overlayCircle.showOverlay(isShowCircle)
            isShowCircle = !isShowCircle
        }

        var isShowMarker = false
        var marker = OverlayMarker(mAMap, mContext)
        marker.createOverlay(dataMarkers[3])
        vMapMarker.setOnClickListener {
            marker.showOverlay(isShowMarker)
            isShowMarker = !isShowMarker
        }
    }

    private fun showMarker(show: Boolean) {
        val iterator = markers.iterator()
        while (iterator.hasNext()) {
            val marker = iterator.next().value
            marker.showMarkerSignage(show)
        }
    }

    private fun initFlowable() {
        launch = lifecycleScope.launch {
            while (true) {
                sendData()
                delay(timeMillis = 3000)
            }
        }
    }

    private fun sendData() {

        for (dataMarker: DataMarker in dataMarkers) {
            dataMarker.latLng = latlngs[random.nextInt(4)]
            dataMarker.rotate = random.nextInt(360).toFloat()
            dataMarker.height = random.nextInt(1000).toFloat()
            dataMarker.speed = random.nextInt(300).toFloat()

            if (markers.contains(dataMarker.name)) {
                markers[dataMarker.name]?.startMove(dataMarker)
            } else {
                val marker = OverlayMarkerMove(mContext, mAMap)
                marker.initMarker(dataMarkers[random.nextInt(4)],show)
                markers[dataMarker.name] = marker
            }
        }
    }

    private fun setMapUI() {
        mAMap.uiSettings.isZoomControlsEnabled = false
        mAMap.uiSettings.isScaleControlsEnabled = false
        mAMap.uiSettings.isTiltGesturesEnabled = false
        mAMap.uiSettings.isRotateGesturesEnabled = false
    }

    override fun initData() {


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vMapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        vMapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        vMapView.onPause()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        vMapView.onDestroy()
//    }
}


