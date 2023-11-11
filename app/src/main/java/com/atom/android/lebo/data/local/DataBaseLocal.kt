package com.atom.android.lebo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CartEntityLocal::class, FavoriteEntityLocal::class],
    version = DataBaseLocal.VERSION
)
abstract class DataBaseLocal : RoomDatabase() {

    abstract val cartDao: CartEntityLocalDAO
    abstract val favoriteDao: FavoriteEntityLocalDAO

    companion object {
        const val NAME = "LeBo"
        const val VERSION = 1
        const val TABLE_FAVORITE = "favorite"
        const val TABLE_CART = "cart"
    }
}
