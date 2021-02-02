package com.sai.module_map

import android.content.Context
import android.graphics.Point
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.CoordinateConverter
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds

class MapUtils(val context: Context, val aMap: AMap) {

    private val coordinateConverterGPS by lazy {
        CoordinateConverter(context).from(CoordinateConverter.CoordType.GPS)
    }

    // AMapUtils 提供：坐标转换
    // 距离计算：单位米 float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
    // 面积计算 单位平方米 float area = AMapUtils.calculateArea(leftTopLatlng,rightBottomLatlng);
    // 面积计算 float area = AMapUtils.calculateArea(List<LatLng>);

    //返回一个从地图位置转换来的屏幕位置。
    //	Projection.toScreenLocation(LatLng paramLatLng)

    //判断圆是否包含传入的经纬度点。
//    Circle.contains(LatLng latLng)

//    LatLngBounds.contains(LatLng point)
//    判断矩形区域是否包含传入的经纬度点。

//    Polygon.contains(LatLng latLng)
//    判断多边形是否包含传入的经纬度点。

    //  SpatialRelationUtil  计算点到线最短距离工具类

    /**
     * 将屏幕上的位置 point 转化为 LatLng
     */
    fun converterPoint(point: Point): LatLng {
        val projection = aMap.projection
        return projection.fromScreenLocation(point)
    }

    /**
     * 在屏幕中心显示所有点
     */
    fun showAllPosition(latlngs: MutableList<LatLng>) {
        val builder = LatLngBounds.builder()
        for (latlng in latlngs) {
            builder.include(latlng)
        }
        // padding 500个像素
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 500))
    }

    /**
     * 将GPS转化成高德坐标
     */
    fun converterGPS(latLng: LatLng): LatLng {
        return coordinateConverterGPS.coord(latLng).convert()
    }

}