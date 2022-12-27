package com.inmar.compose1.data

import android.app.Application
import com.inmar.compose1.database.PensionesRepository
import com.inmar.compose1.database.PensionesRoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PensionesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { PensionesRoomDB.getDatabase(this,applicationScope) }
    val repository by lazy { PensionesRepository(database.consultafoliosdao()) }

}