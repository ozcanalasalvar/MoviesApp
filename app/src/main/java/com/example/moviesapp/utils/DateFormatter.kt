package com.example.moviesapp.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatter {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun formatDateString(dateString: String): String {
            val sdf =
                SimpleDateFormat("yyyy-MM-dd")
            val out =
                SimpleDateFormat("dd.MM.yyyy")

            val date: Date = sdf.parse(dateString)

            return out.format(date)
        }

        fun getYearFromDateString(dateString: String): String {
            return dateString.split("-")[0]
        }

    }
}