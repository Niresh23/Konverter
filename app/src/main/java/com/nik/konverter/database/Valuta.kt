package com.nik.konverter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Valuta(
    @PrimaryKey
    private val id: String,
    @ColumnInfo
    private val name: String,
    @ColumnInfo
    private val charCode: String,
    @ColumnInfo
    private val value: String)


