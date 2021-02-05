package com.sai.module_map.real

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.amap.api.maps.model.LatLng
import com.component.module_basis.http.RetrofitClient
import com.sai.module_map.R
import com.sai.module_map.data.DataMarker
import com.sai.module_map.http.AdsbApiService
import com.sai.module_map.http.ResAdsbData
import com.sai.module_map.overlay.OverlayMarkerMove
import kotlinx.android.synthetic.main.fragment_map.vMapMarkerShow
import kotlinx.android.synthetic.main.fragment_map.vMapMarkerStartMove
import kotlinx.android.synthetic.main.fragment_map.vMapView
import kotlinx.android.synthetic.main.fragment_map_real.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapRealFragment : Fragment() {

    private var move = true
    private var show = false
    private var send = true
    private val adsb = mutableListOf<DataMarker>()

    private val mAMap by lazy { vMapView.map }
    private lateinit var launch: Job
    private lateinit var launchSend: Job
    lateinit var mContext: Context

    private val latlngs: MutableList<LatLng> by lazy {
        val list = mutableListOf<LatLng>()

        list.add(LatLng(39.970273, 116.358593))
        list.add(LatLng(39.939745, 116.258343))
        list.add(LatLng(39.896561, 116.388806))
        list.add(LatLng(39.935533, 116.462963))
        list
    }

    private val apiService by lazy {
        RetrofitClient.instance.onCreateApiService(AdsbApiService::class.java, AdsbApiService.baseUrl)
    }
    private val markers: MutableMap<String, OverlayMarkerMove> by lazy {
        mutableMapOf()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map_real, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vMapView.onCreate(savedInstanceState)
        setMapUI()

        vMapMarkerStartMove.setOnClickListener {

            if (move) getData()
            else launch.cancel()

            move = !move
        }
        vMapMarkerShow.setOnClickListener {

            if (markers.isNotEmpty()) {
                show = !show

                for (entry in markers.entries) {
                    entry.value.showMarkerSignage(show)
                }
            }
        }
        vSendAdsb.setOnClickListener {
            if (send) sendData()
            else launchSend.cancel()

            send = !send
        }
    }
    private val random = java.util.Random()
    private var num = 0
    private fun sendData() {
        launchSend = lifecycleScope.launch {
            while (true) {
                val latLng = latlngs[num%4]
                val data = ResAdsbData( latitude = latLng.latitude,
                    longitude = latLng.longitude,
                    speed = random.nextInt(300).toFloat() ,
                    azimuth =  random.nextInt(360).toFloat(),
                    altitude =  random.nextInt(500).toFloat(),
                    atcCode = "No.1",
                    id = 1,
                    time = System.currentTimeMillis())
                apiService.sendAdsbData(data)
                delay(timeMillis = 3000)
                num++
            }
        }
    }
    private fun getData() {
        launch = lifecycleScope.launch {
            while (true) {
                getAdsbData()
                delay(timeMillis = 3000)
            }
        }
    }

    private suspend fun getAdsbData() {

        val data = apiService.getAdsbData()
        if (data.code == 200) {
            val res: MutableList<ResAdsbData> = data.data

            adsb.clear()
            for (re in res) {
                adsb.add(DataMarker(
                    name = re.atcCode,
                    rotate = re.azimuth,
                    latLng = LatLng(re.latitude, re.longitude),
                    alert = false,
                    speed = re.speed,
                    height = re.altitude,
                    type = 2
                ))
            }

            for (dataMarker in adsb) {
                if (markers.contains(dataMarker.name)) {
                    //开始移动动画
                    markers[dataMarker.name]!!.startMove(dataMarker)
                } else {
                    // 创建Marker
                    val marker = OverlayMarkerMove(mContext, mAMap)
                    marker.initMarker(dataMarker,show)
                    markers[dataMarker.name] = marker
                }
            }
        }
    }


    private fun setMapUI() {
        mAMap.uiSettings.isZoomControlsEnabled = false
        mAMap.uiSettings.isScaleControlsEnabled = false
        mAMap.uiSettings.isTiltGesturesEnabled = false
        mAMap.uiSettings.isRotateGesturesEnabled = false
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

}