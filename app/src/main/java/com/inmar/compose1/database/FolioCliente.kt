package com.inmar.compose1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folio_cliente")
data class FolioCliente(
    @PrimaryKey @ColumnInfo(name = "id") val _id :String,
    val tip_sol : String,
    val fol     : String,
    val raz     : String,
) {
}