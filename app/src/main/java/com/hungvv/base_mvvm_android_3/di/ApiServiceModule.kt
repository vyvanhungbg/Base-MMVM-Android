package com.hungvv.base_mvvm_android_3.di

import com.hungvv.base_mvvm_android_3.data.model.BaseResponseGitHub
import com.hungvv.base_mvvm_android_3.data.model.User
import com.hungvv.base_mvvm_android_3.utils.constant.APIConstant
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

val apiServiceModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}


interface ApiService{
    @GET(APIConstant.EndPoint.SEARCH_USER)
    suspend fun searchUserByKeyWord(@Query("q") keyWord: String): BaseResponseGitHub<User>?
}