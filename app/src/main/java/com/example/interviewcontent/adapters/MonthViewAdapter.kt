package com.example.interviewcontent.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewcontent.R
import com.example.interviewcontent.models.CalenderResponse

class MonthViewAdapter (private val taskList: CalenderResponse):
    RecyclerView.Adapter<MonthViewAdapter.MonthViewHolder>(){


    inner class MonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}