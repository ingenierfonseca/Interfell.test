package com.example.interfelltest.dblogic

import android.content.Context
import com.example.interfelltest.dblogic.dao.ContraventionDao
import com.example.interfelltest.dblogic.entity.Contravention
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class Repository(context: Context) {
    private var db: Database? = null
    private var contraventioDao: ContraventionDao? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        db = Database.getDatabase(context)
        contraventioDao = db!!.contraventioDao()
    }

    suspend fun insertContravention(item: Contravention) {
        return scope.async {
            contraventioDao!!.insert(item)
        }.await()
    }

    suspend fun getAll(plate: String): List<Contravention> {
        return scope.async {
            contraventioDao!!.getAllPlate(plate)
        }.await()
    }
}