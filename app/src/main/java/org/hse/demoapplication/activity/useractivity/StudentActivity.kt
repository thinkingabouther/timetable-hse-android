package org.hse.demoapplication.activity.useractivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_student.*
import org.hse.demoapplication.R
import org.hse.demoapplication.dbal.entity.GroupEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType
import org.hse.demoapplication.model.spinner.Group
import org.hse.demoapplication.viewmodel.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

class StudentActivity : AbstractUserActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setContentView(R.layout.activity_student)

        initGroupList()
        adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ArrayList<Group>())
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        studentSpinner.adapter = adapter
        studentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("onNothingSelected", "Absolutely nothing...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                Log.d("OnItemSelected", "selectedItem: " + adapter.getItem(position).toString())
                adapter.getItem(position)?.let { onSpinnerItemChanged(it) }
            }
        }

        weekScheduleButton.setOnClickListener {
            showScheduleImpl(ScheduleType.WEEK)
        }
        dayScheduleButton.setOnClickListener {
            showScheduleImpl(ScheduleType.DAY)
        }
        timeLabel = findViewById(R.id.timeLabel)
        super.initTime()
    }

    private fun onSpinnerItemChanged(group: Group) {
        super.showTime()
        mainViewModel.getCurrentTimetableByGroup(group.id, time ?: Date()).observe(this, Observer<TimeTableWithTeacherEntity> {
            initDataFromTimeTable(it)
        })
    }

    private fun showScheduleImpl(type : ScheduleType) {
        val selectedItem = studentSpinner.selectedItem
        if (selectedItem !is Group) return
        super.showSchedule(ScheduleMode.STUDENT, type, selectedItem)
    }

    private fun initGroupList() {
        mainViewModel.getGroups().observe(this, Observer<List<GroupEntity>> {
            val groupsResult = ArrayList<Group>()
            for (listEntity : GroupEntity in it) {
                val group = Group(listEntity.id, listEntity.name)
                groupsResult.add(group)
                Log.d("initGroupList", group.toString())
            }
            adapter.clear()
            adapter.addAll(groupsResult)
        })
    }
}