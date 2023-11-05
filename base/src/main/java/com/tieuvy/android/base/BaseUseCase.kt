package com.tieuvy.android.base

abstract class BaseUseCase<DataRequest: Any, DataResponse:Any> {

    abstract suspend fun execute(request: DataRequest): DataResponse
}