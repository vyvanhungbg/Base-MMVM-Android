package com.atom.android.lebo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteEntityLocalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteEntityLocal): Single<Long>

    @Delete
    fun delete(favorite: FavoriteEntityLocal): Single<Int>

    @Query("SELECT * FROM favorite")
    fun getAll(): Single<List<FavoriteEntityLocal>>

    @Query("SELECT * FROM favorite WHERE idBook =:id")
    fun findByID(id: Int): Single<FavoriteEntityLocal?>

}
