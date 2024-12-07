package com.example.interviewcontent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewcontent.repository.CalanderRepository
import java.util.Calendar

class CalanderViewModel(calanderRepository: CalanderRepository) : ViewModel() {

    var mDaysList = MutableLiveData<List<String>>()
    var mUpdatedMonth = MutableLiveData<Int>()
    var mUpdatedYear = MutableLiveData<Int>()
    fun addDailyTask(date: String, title: String?) {

    }

    fun generateDaysForMonth(selectedYear: Int, selectedMonth: Int){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedYear)
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val daysList = mutableListOf<String>()
        daysList.addAll(daysOfWeek)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        for (i in 1 until firstDayOfWeek) {
            daysList.add("")
        }

        for (day in 1..daysInMonth) {
            daysList.add(day.toString())
        }

        mDaysList.value = daysList
    }

    fun changeTheCalenderView(changedBackwards: Boolean, selectedYear: Int, selectedMonth: Int) {
        var updatedMonth = -1
        var updatedYear = 0
        if(changedBackwards){
            if(selectedMonth == Calendar.JANUARY ){
                updatedMonth = Calendar.DECEMBER
                updatedYear = selectedYear -1
            }else{
                updatedMonth = selectedMonth - 1
                updatedYear = selectedYear
            }
        }else{
            if(selectedMonth == Calendar.DECEMBER ){
                updatedMonth = Calendar.JANUARY
                updatedYear = selectedYear +1
            }else{
                updatedMonth = selectedMonth+1
                updatedYear = selectedYear
            }
        }
       generateDaysForMonth(updatedYear,updatedMonth)
    }
}