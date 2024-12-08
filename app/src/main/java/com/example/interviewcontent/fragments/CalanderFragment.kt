package com.example.interviewcontent.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interviewcontent.CalanderActivity
import com.example.interviewcontent.R
import com.example.interviewcontent.adapters.CustomCalenderViewAdapter
import com.example.interviewcontent.adapters.MonthViewAdapter
import com.example.interviewcontent.adapters.OnItemClick
import com.example.interviewcontent.databinding.FragmentCalanderBinding
import com.example.interviewcontent.models.CalenderResponse
import com.example.interviewcontent.models.Task
import com.example.interviewcontent.models.TaskDetail
import com.example.interviewcontent.resources.Resources
import com.example.interviewcontent.viewmodel.CalanderViewModel
import okhttp3.internal.notify
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class CalanderFragment : Fragment(R.layout.fragment_calander), View.OnClickListener, OnItemClick {

    lateinit var viewModel: CalanderViewModel
    lateinit var _binding: FragmentCalanderBinding
    private val binding get() = _binding
    private var selectedDate = ""
    private var taskToAdd = ""
    private var mSelectedYear = 2021
    private var mSelectedMonth = Calendar.DECEMBER
    var calenderSelectedDate: Int = -1
    var calenderSelectedMonth: Int = -1
    var calenderSelectedYear: Int = -1
    private val TAG = "CalanderFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calander, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CalanderActivity).calanderViewModel
        setClickListeners()
        setLiveDataObservers()
        setupSpinners()
    }

    private fun setLiveDataObservers() {
        viewModel.mUpdatedMonth.observe(viewLifecycleOwner) {
            mSelectedMonth = it
        }
        viewModel.mUpdatedYear.observe(viewLifecycleOwner) {
            mSelectedYear = it
        }
        viewModel.mDaysList.observe(viewLifecycleOwner) {
            binding.customCalendarView.layoutManager = GridLayoutManager(context, 7)
            binding.customCalendarView.adapter = CustomCalenderViewAdapter(it, WeakReference(this))
        }

        viewModel.taskList.observe(viewLifecycleOwner){response->
            when(response){
                is Resources.Error -> {
                   hideProgressBar()
                    if(response.message!=null){
                        Toast.makeText(activity,"Error occured", Toast.LENGTH_LONG)
                    }
                }
                is Resources.Loading -> { showProgressBar()}
                is Resources.Success -> {
                    hideProgressBar()
                    if(response.data !=null) {
                        val monthViewAdapter = MonthViewAdapter(response.data!!)
                        binding.dailyTaskRv.layoutManager = LinearLayoutManager(context)
                        binding.dailyTaskRv.adapter = monthViewAdapter
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.addIcon.setOnClickListener(this)
        binding.leftArrow.setOnClickListener(this)
        binding.rightArrow.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_icon-> {
                val taskBottomSheetDialog = TaskBottomSheetDialog(
                    requireContext(),
                    viewModel // Pass the viewModel directly
                ) { task ->
                    taskToAdd = task
                    viewModel.addDailyTask(selectedDate, task)
                }
                taskBottomSheetDialog.show()
            }
            R.id.left_arrow -> viewModel.changeTheCalenderView(true, mSelectedYear, mSelectedMonth)
            R.id.right_arrow -> viewModel.changeTheCalenderView(false, mSelectedYear, mSelectedMonth)
        }
    }

    private fun setupSpinners() {
        val months = resources.getStringArray(R.array.months) // Define the months in strings.xml
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.monthLabel.adapter = monthAdapter

        val years = (1975..2075).toList()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearLabel.adapter = yearAdapter

        binding.monthLabel.setSelection(mSelectedMonth)
        binding.yearLabel.setSelection(years.indexOf(mSelectedYear))

        viewModel.generateDaysForMonth(mSelectedYear, mSelectedMonth)

        binding.monthLabel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSelectedMonth = position
                viewModel.generateDaysForMonth(mSelectedYear, mSelectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.yearLabel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mSelectedYear = years[position]
                viewModel.generateDaysForMonth(mSelectedYear, mSelectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }

    private fun hideProgressBar() {
        binding.loaderContainer.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.loaderContainer.visibility = View.VISIBLE
    }
    override fun didClickItem(date: String) {
        calenderSelectedDate = date.toIntOrNull() ?: -1
        calenderSelectedMonth = mSelectedMonth
        calenderSelectedYear = mSelectedYear
        binding.customCalendarView.adapter?.notifyDataSetChanged()
    }

    override fun isSelectedDate(date: String): Boolean {
        return mSelectedYear == calenderSelectedYear && mSelectedMonth == calenderSelectedMonth && date.toIntOrNull() == calenderSelectedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun isCurrentDate(date: String): Boolean {
        val currentDate = LocalDate.now()
        return currentDate.year == mSelectedYear && (currentDate.month.value - 1) == mSelectedMonth && currentDate.dayOfMonth == date.toIntOrNull()
    }
}
