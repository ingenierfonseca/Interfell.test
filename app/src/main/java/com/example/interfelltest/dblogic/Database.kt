package com.example.interfelltest.dblogic

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.interfelltest.dblogic.dao.ContraventionDao
import com.example.interfelltest.dblogic.entity.Contravention

@androidx.room.Database(entities = [Contravention::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun contraventioDao(): ContraventionDao
    companion object {
        var instance : Database? = null

        fun getDatabase(context: Context) : Database? {
            if (instance == null) {
                synchronized(Database::class) {
                    instance = Room.databaseBuilder(context.applicationContext, Database::class.java, "Database").build()
                }
            }
            return  instance
        }
    }
}