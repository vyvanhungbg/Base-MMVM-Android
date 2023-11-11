package com.atom.android.lebo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DataBaseLocal.TABLE_CART)
class CartEntityLocal(
    @PrimaryKey
    val idBook: Int,
    val amount: Int,
    val isChecked: Boolean = false
)
