package com.nik.konverter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nik.konverter.model.forms.Valute

@Entity
data class Valuta(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val charCode: String,
    @ColumnInfo
    val value: String)


