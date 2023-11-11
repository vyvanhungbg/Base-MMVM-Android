package com.atom.android.lebo.data.local

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DataBaseLocal.TABLE_FAVORITE)
class FavoriteEntityLocal(
    @PrimaryKey
    val idBook: Int,
    val url: String?,
    val title: String?,
) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FavoriteEntityLocal>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntityLocal,
                newItem: FavoriteEntityLocal
            ) =
                oldItem.idBook == newItem.idBook

            override fun areContentsTheSame(
                oldItem: FavoriteEntityLocal,
                newItem: FavoriteEntityLocal
            ) = oldItem.idBook == newItem.idBook
        }
    }
}
