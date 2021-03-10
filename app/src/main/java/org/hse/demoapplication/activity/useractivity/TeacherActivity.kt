package org.hse.demoapplication.activity.useractivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.hse.demoapplication.R
import org.hse.demoapplication.model.spinner.Teacher
import kotlinx.android.synthetic.main.activity_teacher.*
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType

class TeacherActivity : AbstractUserActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        val groupList = ArrayList<Teacher>()
        initTeachersList(groupList)
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, groupList)
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        teacherSpinner.adapter = adapter
        teacherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("onNothingSelected", "Absolutely nothing...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                Log.d("OnItemSelected", "selectedItem: " + adapter.getItem(position).toString())
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

    private fun initTeachersList(groups : ArrayList<Teacher>) {
        groups.add(Teacher("Привет", "Приветов"));
        groups.add(Teacher("Бенедикт", "Бенедиктов"))
    }

    private fun showScheduleImpl(type : ScheduleType) {
        val selectedItem = teacherSpinner.selectedItem
        if (selectedItem !is Teacher) return
        super.showSchedule(ScheduleMode.TEACHER, type, selectedItem)
    }
}