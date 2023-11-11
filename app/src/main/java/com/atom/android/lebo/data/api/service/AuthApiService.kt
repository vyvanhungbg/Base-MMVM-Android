package com.atom.android.lebo.data.api.service

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Bill
import com.atom.android.lebo.model.Notification
import com.atom.android.lebo.model.Order
import com.atom.android.lebo.model.User
import com.atom.android.lebo.utils.constants.ApiConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {

    interface UserService {

        @GET(ApiConstant.ENDPOINT.USER)
        fun getUser(): Single<BaseResponse<User?>>

        @FormUrlEncoded
        @POST(ApiConstant.ENDPOINT.CHANGE_PASSWORD)
        fun changePassword(
            @Field(ApiConstant.FILED.OLD_PASSWORD) oldPassword: String,
            @Field(ApiConstant.FILED.NEW_PASSWORD) newPassword: String
        ): Single<BaseResponse<String?>>

        @FormUrlEncoded
        @PUT(ApiConstant.ENDPOINT.REGISTER_NOTIFICATION)
        fun registerNotification(
            @Field(ApiConstant.FILED.TOKEN) token: String
        ): Single<BaseResponse<String?>>

        @FormUrlEncoded
        @PUT(ApiConstant.ENDPOINT.UNREGISTER_NOTIFICATION)
        fun unregisterNotification(
            @Field(ApiConstant.FILED.TOKEN) token: String
        ): Single<BaseResponse<String?>>
    }

    interface BillService {

        @POST(ApiConstant.ENDPOINT.ORDER)
        fun createOrder(@Body order: Order): Single<BaseResponse<Bill>>

        @GET(ApiConstant.ENDPOINT.ORDER_USER)
        fun getBill(@Query(ApiConstant.FILED.ID_BOOK) idBook: Int): Single<BaseResponse<List<Bill>?>>

        @GET("${ApiConstant.ENDPOINT.ORDER}/{id}")
        fun getBillByID(@Path(ApiConstant.FILED.ID) id: Int): Single<BaseResponse<Bill>>
    }

    interface NotificationService {
        @GET(ApiConstant.ENDPOINT.NOTIFICATION)
        fun getNotification(): Single<BaseResponse<List<Notification>?>>

        @PUT("${ApiConstant.ENDPOINT.NOTIFICATION}/{id}")
        fun updateNotification(@Path(ApiConstant.FILED.ID) id: Int): Single<BaseResponse<String?>>
    }
}
