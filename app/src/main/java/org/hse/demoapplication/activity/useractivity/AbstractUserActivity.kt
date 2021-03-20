package org.hse.demoapplication.activity.useractivity

import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_teacher.*
import okhttp3.*
import org.hse.demoapplication.R
import org.hse.demoapplication.activity.scheduleactivity.ScheduleActivity
import org.hse.demoapplication.dbal.entity.TimeTableWithTeacherEntity
import org.hse.demoapplication.viewmodel.MainViewModel
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType
import org.hse.demoapplication.model.json.TimeResponse
import org.hse.demoapplication.model.spinner.Group
import org.hse.demoapplication.viewmodel.DateViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class AbstractUserActivity : AppCompatActivity() {

    private val TAG = "AbstractUserActivity"
    private val URL = getString(R.string.apiTimeUrl)
    protected lateinit var timeLabel : TextView
    protected var time : Date? = null
    protected lateinit var mainViewModel : MainViewModel
    lateinit var adapter : ArrayAdapter<Group>
    protected val dateViewModel: DateViewModel by viewModels()

    private val client : OkHttpClient = OkHttpClient()

    protected fun showSchedule(mode : ScheduleMode, type: ScheduleType, item: Group) {
        intent = Intent(this, ScheduleActivity::class.java)
        intent.putExtra(ScheduleActivity.ARG_ID, item.id)
        intent.putExtra(ScheduleActivity.ARG_TYPE, type)
        intent.putExtra(ScheduleActivity.ARG_MODE, mode)
        intent.putExtra(ScheduleActivity.ARG_NAME, item.name)
        intent.putExtra(ScheduleActivity.ARG_TIME,
            SimpleDateFormat(getString(R.string.transferDateFormat), Locale("ru")).format(time?: Date()))

        startActivity(intent)
    }

    protected fun initTime() {
        getTime()
    }

    private fun getTime() {
        val request = Request.Builder().url(URL).build()
        val call = client.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "getTime", e)
            }
            override fun onResponse(call: Call, response: Response) {
                parseResponse(response)
            }
        })
    }

    private fun parseResponse(response: Response) {
        val gson = Gson()
        val body = response.body
        try {
            if (body == null) {
                return
            }
            val string = body.string()
            Log.d(TAG, string)
            val timeResponse = gson.fromJson<TimeResponse>(string, TimeResponse::class.java)
            val currentTimeVal = timeResponse.getTimeZone().getCurrentTime()
            val simpleDateFormat = SimpleDateFormat(getString(R.string.parseResponseDateFormat), Locale("en"))
            val dateTime = simpleDateFormat.parse(currentTimeVal)
            dateTime?.also {
                runOnUiThread {
                    showTime()
                    dateViewModel.currentDate.value = it
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed to parse response", e)
        }
    }

    protected open fun showTime() {
        val nameObserver = Observer<Date> { newDate ->
            val simpleDateFormat = SimpleDateFormat(getString(R.string.showTimeDateFormat), Locale("ru"))
            timeLabel.text = getString(R.string.timeLabel_text, simpleDateFormat.format(newDate))
            time = newDate
        }
        dateViewModel.currentDate.observe(this, nameObserver)
    }

    protected fun initDataFromTimeTable(listEntity: TimeTableWithTeacherEntity?) {
        Log.d("initDataFromTimeTable", "inside")
        if (listEntity != null) {
            currentLessonStateLabel.text = getString(R.string.currentLessonStateLabelYes_text)
            val timetable = listEntity.timeTableEntity
            disciplineLabel.text = timetable.subjName
            cabinetLabel.text = timetable.cabinet
            buildingLabel.text = timetable.corp
            teacherLabel.text = listEntity.teacherEntity.fio
        }
        else {
            currentLessonStateLabel.text = getString(R.string.currentLessonStateLabelNo_text)
            disciplineLabel.text = getString(R.string.disciplineLabel_text)
            cabinetLabel.text = getString(R.string.cabinetLabel_text)
            buildingLabel.text = getString(R.string.buildingLabel_text)
            teacherLabel.text = getString(R.string.teacherLabel_text)
        }
    }
}