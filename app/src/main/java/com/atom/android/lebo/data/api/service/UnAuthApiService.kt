package com.atom.android.lebo.data.api.service

import com.atom.android.lebo.base.BaseResponse
import com.atom.android.lebo.model.Author
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.model.Genre
import com.atom.android.lebo.model.LoginEntity
import com.atom.android.lebo.model.ShippingMethod
import com.atom.android.lebo.model.Slider
import com.atom.android.lebo.utils.constants.ApiConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnAuthApiService {

    interface LoginService {

        @POST(ApiConstant.ENDPOINT.LOGIN)
        fun login(@Body loginEntity: LoginEntity): Single<BaseResponse<String?>>

        @GET(ApiConstant.ENDPOINT.CHECK_LOGIN)
        fun checkLogin(): Single<BaseResponse<String?>>

        @POST(ApiConstant.ENDPOINT.GENERATE_OTP)
        fun requestOTPLogin(@Query(ApiConstant.FILED.EMAIL) email: String?): Single<BaseResponse<String?>>
    }

    interface ForgotPasswordService {

        @FormUrlEncoded
        @POST(ApiConstant.ENDPOINT.FORGOT_PASSWORD)
        fun requestForgotPassword(@Field(ApiConstant.FILED.EMAIL) email: String): Single<BaseResponse<String?>>
    }

    interface AuthorService {

        @GET(ApiConstant.ENDPOINT.AUTHOR)
        fun getAuthor(): Single<BaseResponse<List<Author>?>>
    }

    interface BookService {

        @GET(ApiConstant.ENDPOINT.GENRE)
        fun getGenre(): Single<BaseResponse<List<Genre>?>>

        @GET(ApiConstant.ENDPOINT.BOOK)
        fun getBook(@Query(ApiConstant.FILED.PAGE) page: Int): Single<BaseResponse<List<Book>?>>

        @GET(ApiConstant.ENDPOINT.BANNER)
        fun getSlider(): Single<BaseResponse<List<Slider>?>>

        @GET("${ApiConstant.ENDPOINT.BOOK}/{id}")
        fun getBookByID(@Path(ApiConstant.FILED.ID) id: Int): Single<BaseResponse<Book?>>

        @GET(ApiConstant.ENDPOINT.BOOK_RELATE)
        fun getBookRelate(@Query(ApiConstant.FILED.ID_BOOK) type: Int): Single<BaseResponse<List<Book>?>>

        @POST(ApiConstant.ENDPOINT.CART)
        fun getBooksByID(@Body id: List<Int>): Single<BaseResponse<List<Book>?>>
    }

    interface ShippingMethodService {
        @GET("${ApiConstant.ENDPOINT.SHIPPING_METHOD}/{id}")
        fun getMoneyShip(@Path(ApiConstant.FILED.ID) id: Int): Single<BaseResponse<ShippingMethod?>>
    }
}
