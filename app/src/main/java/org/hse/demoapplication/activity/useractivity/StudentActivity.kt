package org.hse.demoapplication.activity.useractivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.hse.demoapplication.R
import org.hse.demoapplication.factory.GroupFactory
import org.hse.demoapplication.model.spinner.Group
import kotlinx.android.synthetic.main.activity_student.*
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType

class StudentActivity : AbstractUserActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val groupList = ArrayList<Group>()
        initGroupList(groupList)

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, groupList)

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        studentSpinner.adapter = adapter
        studentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    private fun showScheduleImpl(type : ScheduleType) {
        val selectedItem = studentSpinner.selectedItem
        if (selectedItem !is Group) return
        super.showSchedule(ScheduleMode.STUDENT, type, selectedItem)
    }

    private fun initGroupList(groups : ArrayList<Group>) {
        groups += GroupFactory.createList("ПИ", 18, 3);
        groups += GroupFactory.createList("БИ", 20, 2)
    }
}