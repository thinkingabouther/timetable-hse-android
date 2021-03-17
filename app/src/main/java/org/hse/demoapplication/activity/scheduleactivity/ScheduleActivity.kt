package org.hse.demoapplication.activity.scheduleactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.hse.demoapplication.R
import org.hse.demoapplication.model.schedule.ScheduleItem
import org.hse.demoapplication.model.schedule.ScheduleItemHeader
import kotlinx.android.synthetic.main.activity_schedule.*
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType


class ScheduleActivity : AppCompatActivity(), OnItemClick {
    companion object {
        const val ARG_TYPE = "TYPE"
        const val ARG_ID = "ID"
        const val ARG_MODE = "MODE"
        const val ARG_NAME = "NAME"
        const val ARG_TIME = "TIME"
        const val DEFAULT_ID = 0
    }

    private lateinit var adapter : ItemAdapter
    lateinit var type : ScheduleType
    lateinit var mode : ScheduleMode
    var id : Int = DEFAULT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        type = intent.getSerializableExtra(ARG_TYPE) as ScheduleType
        mode = intent.getSerializableExtra(ARG_MODE) as ScheduleMode
        scheduleTitleTextView.text = intent.getSerializableExtra(ARG_NAME) as String
        timeTextView.text = intent.getSerializableExtra(ARG_TIME) as String
        id = intent.getIntExtra(ARG_ID, DEFAULT_ID)
        scheduleRecyclerView.layoutManager = LinearLayoutManager(this)
        scheduleRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = ItemAdapter(this)
        scheduleRecyclerView.adapter = adapter
        initData()
    }


    private fun initData() {
        val list = ArrayList<ScheduleItem>()
        val header = ScheduleItemHeader()
        header.title = "Понедельник, 28 января"
        list.add(header)

        var item = ScheduleItem()
        item.start = "10:00"
        item.end = "11:00"
        item.type = "Практическое занятие"
        item.name = "Анализм данных (анг)"
        item.place = "Ауд. 503, Кочновский пр-д, д.3"
        item.teacher = "Проф. Гущин Михаил Иванович"
        list.add(item)

        item = ScheduleItem()
        item.start = "12:00"
        item.end = "13:00"
        item.type = "Практическое занятие"
        item.name = "Анализ данных (анг)"
        item.place = "Ауд. 503, Кочновский пр-д, д.3"
        item.teacher = "Проф. Гущин Михаил Иванович"
        list.add(item)

        adapter.dataList = list

    }

    override fun onClick(data: ScheduleItem) {
        Log.d("OnClick", data.toString())
    }
}