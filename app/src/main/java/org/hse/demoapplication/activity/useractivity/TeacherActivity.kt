package org.hse.demoapplication.activity.useractivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_teacher.*
import org.hse.demoapplication.R
import org.hse.demoapplication.dbal.entity.TeacherEntity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import org.hse.demoapplication.viewmodel.MainViewModel
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType
import org.hse.demoapplication.model.spinner.Group
import java.util.*
import kotlin.collections.ArrayList

class TeacherActivity : AbstractUserActivity() {


    var selectedGroup : Group? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        val groupList = ArrayList<Group>()
        adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, groupList)
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        initTeachersList()
        teacherSpinner.adapter = adapter
        teacherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("onNothingSelected", "Absolutely nothing...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                Log.d("OnItemSelected", "selectedItem: " + adapter.getItem(position).toString())
                selectedGroup = adapter.getItem(position)
            }
        }
        weekScheduleButton.setOnClickListener {
            showScheduleImpl(ScheduleType.WEEK)
        }
        dayScheduleButton.setOnClickListener {
            showScheduleImpl(ScheduleType.DAY)
        }
        timeLabel = findViewById(R.id.timeLabel);
        super.initTime()
    }

    private fun initTeachersList() {
        mainViewModel.getTeachers().observe(this, Observer<List<TeacherEntity>> {
            val groupsResult = ArrayList<Group>()
            for (listEntity : TeacherEntity in it) {
                val group = Group(listEntity.id, listEntity.fio)
                groupsResult.add(group)
                Log.d("initGroupList", group.toString())
            }
            adapter.clear();
            adapter.addAll(groupsResult)
        })
    }

    private fun showScheduleImpl(type : ScheduleType) {
        val selectedItem = teacherSpinner.selectedItem
        if (selectedItem !is Group) return
        super.showSchedule(ScheduleMode.TEACHER, type, selectedItem)
    }

    override fun showTime(dateTime: Date) {
        super.showTime(dateTime)
        mainViewModel.getTimeTableTeacherByDate(dateTime).observe(this, Observer<List<TimeTableWithTeacherEntity>> {
            for (listEntity: TimeTableWithTeacherEntity in it) {
                Log.d("showTime", listEntity.timeTableEntity.subjName + " " + listEntity.teacherEntity.fio)
                if (selectedGroup?.id == listEntity.timeTableEntity.teacherId) {
                    initDataFromTimeTable(listEntity)
                }
            }
        })
    }
}