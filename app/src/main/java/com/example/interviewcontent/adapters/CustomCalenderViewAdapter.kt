package com.example.interviewcontent.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewcontent.R

class CustomCalenderViewAdapter(private val days: List<String>) :
    RecyclerView.Adapter<CustomCalenderViewAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.date_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = days[position]
        holder.dateText.text = day
        if (position < 7) {
            holder.dateText.setTextColor(Color.BLUE)
            holder.dateText.setTypeface(null, Typeface.BOLD)
        } else if (day.isEmpty()) { // Padding slots
            holder.dateText.visibility = View.INVISIBLE
        } else {
            holder.dateText.setTextColor(Color.BLACK)
            holder.dateText.setTypeface(null, Typeface.NORMAL)
        }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = days.size
}
