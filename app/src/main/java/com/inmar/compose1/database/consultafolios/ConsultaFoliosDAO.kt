package com.inmar.compose1.database.consultafolios

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//@Dao
//interface PensionesDAO{
//    val consultaFoliosDAO get() = ConsultaFoliosDAO
//    val loginDAO get() = LoginDAO
//}

@Dao
interface ConsultaFoliosDAO {
    //interface ConsultaFoliosDAO : PensionesDAO {

//    companion object{
//        @Query("SELECT * FROM folio_cliente ORDER BY dateMilis ASC")
//        fun getFoliosByOrientacion( orientacion : Int): Flow<List<FolioCliente>> {
//        }
//    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolioFromServer( folioCliente: FolioCliente)

    @Query("DELETE FROM folio_cliente")
    suspend fun deleteAll()

//    @Query("SELECT * FROM folio_cliente ORDER BY dateMilis ASC")
//    fun getFoliosByOrientacion( orientacion : Int): Flow<List<FolioCliente>>
}

//interface LoginDAO : PensionesDAO{
//    companion object{
//
//    }
//}