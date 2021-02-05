package com.sai.module_map.http

data class ResAdsbData(
     val id:Int,
     val azimuth:Float,
     val speed:Float,
     val altitude:Float,
     val latitude:Double,
     val longitude:Double,
     val time:Long,
     val atcCode:String)
