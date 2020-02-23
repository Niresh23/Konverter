package com.nik.konverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable

@Dao
interface ValutaDao {
    @Insert
    fun insert(valuta: List<Valuta>): Completable

    @Update
    fun update(valuta: List<Valuta>): Completable

    @Query("SELECT * FROM Valuta WHERE id in () ")
}