package com.atom.android.lebo.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.atom.android.lebo.utils.constants.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val image: String?,
    val isbn: String,
    val description: String,
    val genres: List<Genre>,
    val language: String?,
    val numPages: Int?,
    val price: Double,
    val publicationDate: String?,
    val title: String,
    val availableQuantity: Int?,
    val bookAuthors: List<Author>,
    val amount: Int = 0,
    val isChecked: Boolean = false
) : Parcelable {

    fun getAllNameGenres() = genres.sortedBy { it.name }
        .fold(Constant.DEFAULT.STRING) { init, item ->
            init.plus("${Constant.SLASH} ${item.name}")
        }.removePrefix(Constant.SLASH)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
        }
    }
}
