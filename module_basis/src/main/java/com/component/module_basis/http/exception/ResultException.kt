package com.component.module_basis.http.exception

import java.lang.Exception

class ResultException(var errorCode: Int, var errorMsg: String) : Exception(errorMsg)