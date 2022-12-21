package com.inmar.compose1.database

import androidx.annotation.WorkerThread
import com.inmar.compose1.database.consultafolios.ConsultaFoliosDAO
import com.inmar.compose1.database.consultafolios.FolioCliente
import kotlinx.coroutines.flow.Flow

//import com.inmar.compose1.database.consultafolios.PensionesDAO

class PensionesRepository(private val consultaFoliosDAO: ConsultaFoliosDAO) {

    //val listaFolios : Flow<List<FolioCliente>> = consultaFoliosDAO.getFoliosByOrientacion(2)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFolioFromServer(folioCliente: FolioCliente,
                                      consultaFoliosDAO: ConsultaFoliosDAO){
        consultaFoliosDAO.insertFolioFromServer(folioCliente)
    }
}