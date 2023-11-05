package com.hungvv.base_mvvm_android_3.data.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import java.lang.reflect.Type

@Entity
data class SearchCacheLocal(
    @Id
    var id: Long = 0,
    var keySearch: String = "",
    @Convert(converter = SearchCacheLocalConverter::class, dbType = String::class)
    var data : BaseResponseGitHub<User>? = null
)

class SearchCacheLocalConverter : PropertyConverter<BaseResponseGitHub<User>?, String?> {

    private val jsonConfig = Gson()
    override fun convertToEntityProperty(databaseValue: String?): BaseResponseGitHub<User>? {
        return if (databaseValue != null) {
            val type: Type = object : TypeToken<BaseResponseGitHub<User>>() {}.type
            jsonConfig.fromJson<BaseResponseGitHub<User>>(databaseValue, type)
        }else{
            null
        }
    }

    override fun convertToDatabaseValue(entityProperty: BaseResponseGitHub<User>?): String {
        return jsonConfig.toJson(entityProperty)
    }
}