package com.inmar.compose1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inmar.compose1.database.consultafolios.ConsultaFoliosDAO
import com.inmar.compose1.database.consultafolios.FolioCliente

@Database(entities = arrayOf(FolioCliente::class), version = 1, exportSchema = false)
public abstract class PensionesRoomDB : RoomDatabase(){

    abstract fun consultafoliosdao() : ConsultaFoliosDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PensionesRoomDB? = null

        fun getDatabase(context: Context): PensionesRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PensionesRoomDB::class.java,
                    "pensiones_room_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}