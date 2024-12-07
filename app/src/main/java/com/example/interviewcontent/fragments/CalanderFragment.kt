package com.example.interviewcontent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.interviewcontent.CalanderActivity
import com.example.interviewcontent.R
import com.example.interviewcontent.adapters.CustomCalenderViewAdapter
import com.example.interviewcontent.adapters.MonthViewAdapter
import com.example.interviewcontent.databinding.FragmentCalanderBinding
import com.example.interviewcontent.viewmodel.CalanderViewModel
import java.util.Calendar

class CalanderFragment(): Fragment(R.layout.fragment_calander), View.OnClickListener {

    lateinit var viewModel: CalanderViewModel
    lateinit var _binding: FragmentCalanderBinding
    private val binding get() = _binding
    private lateinit var adapter: MonthViewAdapter
    private var selectedDate = ""
    private var taskToAdd = ""
    private var mSelectedYear = 2021
    private var mSelectedMonth = Calendar.DECEMBER
    val TAG = "CalanderFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_calander,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CalanderActivity).calanderViewModel
        setClickListeners()
        setLiveDataObservers()
    }

    private fun setLiveDataObservers() {
        viewModel.mUpdatedMonth.observe(viewLifecycleOwner) {
            mSelectedMonth = it
        }
        viewModel.mUpdatedYear.observe(viewLifecycleOwner) {
            mSelectedYear = it
        }
        viewModel.generateDaysForMonth(mSelectedYear,mSelectedMonth)
        viewModel.mDaysList.observe(viewLifecycleOwner) {
            binding.customCalendarView.layoutManager = GridLayoutManager(context, 7)
            binding.customCalendarView.adapter = CustomCalenderViewAdapter(it)
        }
    }

    private fun setClickListeners() {
        binding.addIcon.setOnClickListener(this)
        binding.leftArrow.setOnClickListener(this)
        binding.rightArrow.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add_icon -> viewModel.addDailyTask(selectedDate,taskToAdd)
            R.id.left_arrow->{
                viewModel.changeTheCalenderView(true,mSelectedYear,mSelectedMonth)
            }
            R.id.right_arrow->{
                viewModel.changeTheCalenderView(false,mSelectedYear,mSelectedMonth)
            }
        }
    }

}

