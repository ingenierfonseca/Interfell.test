package com.example.interfelltest.dblogic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.interfelltest.dblogic.entity.Contravention

@Dao
interface ContraventionDao {
    @Insert
    fun insert(item: Contravention)

    @Query("SELECT * FROM Contravention WHERE plate==:plate AND dateTime==:dateTime AND isContravention=1")
    fun getContravention(plate: String, dateTime: String): Contravention

    @Query("SELECT * FROM Contravention WHERE plate==:plate")
    fun getAllPlate(plate: String): List<Contravention>
}