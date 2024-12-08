package com.example.interviewcontent.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewcontent.R
import com.example.interviewcontent.models.CalenderResponse
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MonthViewAdapter (private val taskList: CalenderResponse):
    RecyclerView.Adapter<MonthViewAdapter.MonthViewHolder>(){


    inner class MonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.task_name)
        val taskDescription: TextView = view.findViewById(R.id.task_description)
        val date : TextView = view.findViewById(R.id.date_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout_item, parent, false)
        return MonthViewHolder(view)    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val task = taskList.tasks[position]
        val day = task.task_detail?.date?.let { validateDateAndExtractDay(it) }

        holder.taskName.text = task.task_detail.title
        holder.taskDescription.text = task.task_detail.description
        if(day != ""){
            holder.date.text = day
        }

        holder.taskName.setOnClickListener {
            if (holder.taskDescription.visibility == View.GONE) {
                holder.taskDescription.visibility = View.VISIBLE
            } else {
                holder.taskDescription.visibility = View.GONE
            }
        }
    }

    private fun validateDateAndExtractDay(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return try {
            val parsedDate = dateFormat.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = parsedDate
            calendar.get(Calendar.DAY_OF_MONTH).toString()
        }catch (e: Exception){
            return ""
        }
    }
}
