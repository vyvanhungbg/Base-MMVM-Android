package com.atom.android.lebo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Single

@Dao
interface CartEntityLocalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: CartEntityLocal): Single<Long>

    @Delete
    fun delete(cart: CartEntityLocal): Single<Int>

    @Query("SELECT * FROM cart")
    fun getAll(): Single<List<CartEntityLocal>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cart: CartEntityLocal): Single<Int>

    @Query("SELECT * FROM cart WHERE idBook =:id")
    fun findByID(id: Int): Single<CartEntityLocal?>

    @Query("UPDATE cart SET isChecked =:isChecked")
    fun updateAllChecked(isChecked: Boolean): Single<Int>

    @Query("DELETE from cart WHERE idBook IN (:id)")
    fun deleteByIDs(id: List<Int>): Single<Int>

    @Query("SELECT SUM(amount) from cart")
    fun getTotalAmount(): Single<Int>
}
