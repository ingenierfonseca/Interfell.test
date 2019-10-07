package com.example.interfelltest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.interfelltest.common.UtilDate
import com.example.interfelltest.dblogic.Repository
import com.example.interfelltest.dblogic.entity.Contravention
import com.example.interfelltest.view.IRegisterResultCallBack
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterViewModel(context: Context, private val listener: IRegisterResultCallBack) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val repository = Repository(context)

    fun Register(plate: String, dateTime: String, boolean: Boolean) = scope.launch {
        val item = Contravention(0, plate, dateTime, boolean, false)

        withContext(Dispatchers.IO) {
            if (!boolean)
                checkIsContravention(item)
            repository.insertContravention(item)
            LoadDetails(item)
            withContext(Dispatchers.Main) {
                listener.onRegister()
            }
        }
    }

    fun checkIsContravention(item: Contravention) {
        if (checkIsSchedulePico(item.dateTime)) {
            if (checkIsPlateContravention(item.plate, item.dateTime))
                item.isContravention = true
        }
    }

    fun checkIsSchedulePico(dateTime: String): Boolean {
        val date = UtilDate.ChangeDateToSTRHHMM(dateTime)
        var currentTime = UtilDate.ChangeSTRtoDateTime(dateTime)
        var schedulePico1Start = UtilDate.ChangeSTRtoDateTime("$date 07:00:00")
        var schedulePico1Finish = UtilDate.ChangeSTRtoDateTime("$date 09:30:00")
        var schedulePico2Start = UtilDate.ChangeSTRtoDateTime("$date 16:00:00")
        var schedulePico2Finish = UtilDate.ChangeSTRtoDateTime("$date 19:30:00")

        return ((currentTime in schedulePico1Start..schedulePico1Finish) ||
            (currentTime in schedulePico2Start..schedulePico2Finish))
    }

    private fun checkIsPlateContravention(plate: String, dateTime: String): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = UtilDate.ChangeSTRtoDate(dateTime)

        var dayId = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (dayId == 0)
            dayId = 7

        val lastDigit = plate.get(index = plate.length -1)
        var result = true
        when(dayId) {
            1 -> {
                if (lastDigit == '1' || lastDigit == '2')
                    result = false
            }
            2 -> {
                if (lastDigit == '3' || lastDigit == '4')
                    result = false
            }
            3 -> {
                if (lastDigit == '5' || lastDigit == '6')
                    result = false
            }
            4 -> {
                if (lastDigit == '7' || lastDigit == '8')
                    result = false
            }
            5 -> {
                if (lastDigit == '9' || lastDigit == '0')
                    result = false
            }
        }

        return result
    }

    suspend fun LoadDetails(item: Contravention) = scope.launch {
        withContext(Dispatchers.IO) {
            val its = repository.getAll(item.plate)
            withContext(Dispatchers.Main) {
                listener.LoadDetail(its, item)
            }
        }
    }
}