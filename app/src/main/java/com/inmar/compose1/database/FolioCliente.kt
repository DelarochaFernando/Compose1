package com.inmar.compose1.database

import android.net.nsd.NsdServiceInfo
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.ClientInfoStatus

@Entity(tableName = "folio_cliente")
data class FolioCliente(
    @PrimaryKey @ColumnInfo(name = "id") val _id :String,
    val procprim    : String,
    val fol         : String,
    val poliza      : String,
    val nucleo      : String,
    val idproc_prim : String,
    val estatus     : String,
    val user        : String,
    val sub_proc    : String,
    val ver_app     : String,
    val act         : String,
    val fec_alt     : String,
    val fec_mod     : String,
    val obs         : String,
    val dateMilis   : String,
    val nss         : String,
    val id_sub_proc : String,
    val regimen     : String,
    val pde         : String,
    val nombre      : String,
    val banAvi      : String,
    val flag_aut_ine: String,
    val sub_proc_aux: String,
    val idSet       : String,
    val cve_sob     : String,
    val nom_sob     : String,
    val parent_sob  : String,
    val sex_sob     : String,
    val fecNac_sob  : String,
    val flg_aut_alter:String,
    val intent_aut_alter:String,
    val id_img_aut_alter:String,
    val id_vid_aut_alter:String,
    val fec_aut_alter:String,
    val accu_aut_alter:String,
    val intent_disp_autalter:String,
    val id_estatus:String
) {
}