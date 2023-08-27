package com.tieuvy.android.base_mvvm_android_2.data.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity
data class SearchCacheLocal(
    @Id
    var id: Long = 0,
    var keySearch: String = "",
    @Convert(converter = SearchCacheLocalConverter::class, dbType = String::class)
    var data : BaseResponseGitHub<User>? = null
)

class SearchCacheLocalConverter : PropertyConverter<BaseResponseGitHub<User>?, String?> {


    val jsonConfig = Json {
        isLenient = true
        coerceInputValues = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    override fun convertToEntityProperty(databaseValue: String?): BaseResponseGitHub<User>? {
        return if (databaseValue != null) {
            jsonConfig.decodeFromString<BaseResponseGitHub<User>?>(databaseValue)
        }else{
            null
        }
    }

    override fun convertToDatabaseValue(entityProperty: BaseResponseGitHub<User>?): String {
        return jsonConfig.encodeToString(entityProperty)
    }
}