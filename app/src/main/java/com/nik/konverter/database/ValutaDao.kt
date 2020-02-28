package com.nik.konverter.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ValutaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(valuta: List<Valuta>): Completable

    @Update
    fun update(valuta: List<Valuta>): Completable

    @Query("SELECT * FROM Valuta WHERE id = :valutaID")
    fun getValutaById(valutaID: String): Single<Valuta>

    @Query("SELECT * FROM Valuta")
    fun getValuaList(): Single<List<Valuta>>

    @Query("DELETE FROM Valuta")
    fun deleteAll(): Completable
}