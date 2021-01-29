package com.sai.module_map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.amap.api.maps.model.LatLng
import com.component.module_basis.base.BaseFragment
import com.sai.module_map.marker.MarkerData
import com.sai.module_map.marker.MoveMarker
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapFragment : BaseFragment() {

    private val mAMap by lazy { vMapView.map }
    private val random by lazy { Random(200) }
    private val latlngs : MutableList<LatLng>by lazy {
        val list = mutableListOf<LatLng>()

        list.add( LatLng(39.970273, 116.358593))
        list.add( LatLng(39.939745, 116.258343))
        list.add( LatLng(39.896561, 116.388806))
        list.add( LatLng(39.935533, 116.462963))
        list
    }
    private val datas:  MutableList<MarkerData> by lazy {
        val list = mutableListOf<MarkerData>()
        for (i in 0..3){
            val markerData = MarkerData(
                latlngs[i],2,false,"No.$i",
                random.nextFloat(),random.nextFloat(),random.nextFloat())
            list.add(markerData)
        }
        list
    }
    private val markers :MutableMap<String,MoveMarker> by lazy {
        mutableMapOf()
    }

    override fun getLayoutId() = R.layout.fragment_map

    override fun initView(view: View, savedInstanceState: Bundle?) {

        vMapView.onCreate(savedInstanceState)

        setMapUI()


        var show = false
        var move = true
        vMapMarkerShow.setOnClickListener {

            showMarker(show)
            show = !show
        }
        vMapMarkerStartMove.setOnClickListener {

            if (move) initFlowable()
            else launch.cancel()

            move = !move
        }
    }

    private fun showMarker(show: Boolean) {
        val iterator = markers.iterator()
        while (iterator.hasNext()){
            val marker = iterator.next().value
            marker.showMarkerSignage(show)
        }
    }

    private lateinit var launch :Job
    private fun initFlowable() {
        launch = lifecycleScope.launch {
            while (true) {
                sendData()
                delay(timeMillis = 3000)
            }
        }
    }

    private fun sendData() {

        for(data :MarkerData in datas){
            data.latLng = latlngs[random.nextInt(4)]
            data.rotate = random.nextInt(360).toFloat()
            data.height =  random.nextInt(1000).toFloat()
            data.speed = random.nextInt(300).toFloat()

            if (markers.contains(data.name)) {
                markers[data.name]?.startMove(data)
            }else{
                val marker= MoveMarker(mContext,datas[random.nextInt(4)],mAMap)
                marker.initMarker()
                markers[data.name] = marker
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


