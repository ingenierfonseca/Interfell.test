package com.example.interfelltest.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


class UtilDate {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDateLocal(): String {

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val date = Date()
            return dateFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun ChangeSTRtoDateTime(date: String): Long {
            return try {
                val strFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                strFormat.parse(date)!!.time
            } catch (e: Exception) {
                Date().time
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun ChangeSTRtoDate(date: String): Date {
            return try {
                val strFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                strFormat.parse(date)!!
            } catch (e: Exception) {
                Date()
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun ChangeDateToSTRHHMM(fecha: String): String {
            val date = ChangeSTRtoDate(fecha)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            return dateFormat.format(date)
        }
    }
}