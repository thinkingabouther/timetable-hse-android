package org.hse.demoapplication.activity.scheduleactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.hse.demoapplication.R
import org.hse.demoapplication.model.schedule.ScheduleItem
import org.hse.demoapplication.model.schedule.ScheduleItemHeader
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.coroutines.flow.combineTransform
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType
import org.hse.demoapplication.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ScheduleActivity : AppCompatActivity(), OnItemClick {
    companion object {
        const val ARG_TYPE = "TYPE"
        const val ARG_ID = "ID"
        const val ARG_MODE = "MODE"
        const val ARG_NAME = "NAME"
        const val ARG_TIME = "TIME"
        const val DEFAULT_ID = 0
    }

    protected lateinit var mainViewModel : MainViewModel
    private lateinit var adapter : ItemAdapter
    lateinit var type : ScheduleType
    lateinit var mode : ScheduleMode
    var date : Date? = null
    var id : Int = DEFAULT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setContentView(R.layout.activity_schedule)
        type = intent.getSerializableExtra(ARG_TYPE) as ScheduleType
        mode = intent.getSerializableExtra(ARG_MODE) as ScheduleMode
        scheduleTitleTextView.text = intent.getSerializableExtra(ARG_NAME) as String
        val formatterInput = SimpleDateFormat(getString(R.string.transferDateFormat), Locale("ru"))
        date = formatterInput.parse(intent.getSerializableExtra(ARG_TIME) as String)
        val dateFormatOutput = getString(R.string.timeTextViewDateFormat)
        val formatterOutput = SimpleDateFormat(dateFormatOutput, Locale("ru"))
        timeTextView.text = formatterOutput.format(date?: Date())
        id = intent.getIntExtra(ARG_ID, DEFAULT_ID)
        scheduleRecyclerView.layoutManager = LinearLayoutManager(this)
        scheduleRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = ItemAdapter(this)
        scheduleRecyclerView.adapter = adapter
        requestData()
    }

    private fun requestData() {
        if (type == ScheduleType.DAY) {
            if (mode == ScheduleMode.STUDENT) {
                mainViewModel.getTimetableForDayByGroup(id, date ?: Date()).observe(this, androidx.lifecycle.Observer { list ->
                    processList(list)
                })
            }
            else {
                mainViewModel.getTimetableForDayByTeacher(id, date ?: Date()).observe(this, androidx.lifecycle.Observer { list ->
                    processList(list)
                })
            }
        }
        else {
            if (mode == ScheduleMode.STUDENT) {
                mainViewModel.getTimetableForWeekByGroup(id, date ?: Date()).observe(this, androidx.lifecycle.Observer { list ->
                    processList(list)
                })
            }
            else {
                mainViewModel.getTimetableForWeekByTeacher(id, date ?: Date()).observe(this, androidx.lifecycle.Observer { list ->
                    processList(list)
                })
            }
        }
    }

    private fun processList(list : List<TimeTableWithTeacherEntity>) {
        val listScheduleItem = ArrayList<ScheduleItem>()
        list.forEach {
            listScheduleItem += mapToScheduleItem(it)
        }
        initData(listScheduleItem)
    }



    private fun initData(list : ArrayList<ScheduleItem>) {
        if (list.count() > 0) {
            val dateFormat = getString(R.string.scheduleHeaderDateFormat)
            val formatter = SimpleDateFormat(dateFormat, Locale("ru"))
            val firstHeader = ScheduleItemHeader()
            var previousDate = list[0].dateStart
            firstHeader.title = formatter.format(previousDate)
            val listForAdapter = ArrayList<ScheduleItem>()
            listForAdapter.add(firstHeader)
            list.forEach {
                if (it.dateStart.day != previousDate.day) {
                    val header = ScheduleItemHeader()
                    previousDate = it.dateStart
                    header.title = formatter.format(previousDate)
                    listForAdapter.add(header)
                }
                listForAdapter.add(it)
            }
            adapter.dataList = listForAdapter
        }
    }

    private fun mapToScheduleItem(dto : TimeTableWithTeacherEntity) : ScheduleItem {
        val item = ScheduleItem()
        val dateFormat = getString(R.string.scheduleItemDateFormat)
        val formatter = SimpleDateFormat(dateFormat, Locale("ru"))
        item.start = formatter.format(dto.timeTableEntity.timeStart!!)
        item.end = formatter.format(dto.timeTableEntity.timeEnd!!)
        item.type = dto.timeTableEntity.type
        item.name = dto.timeTableEntity.subjName
        item.place = dto.timeTableEntity.cabinet
        item.teacher = dto.teacherEntity.fio
        item.dateStart = dto.timeTableEntity.timeStart
        return item
    }

    override fun onClick(data: ScheduleItem) {
        Log.d("OnClick", data.toString())
    }
}