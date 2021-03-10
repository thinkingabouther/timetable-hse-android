package org.hse.demoapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.hse.demoapplication.R
import org.hse.demoapplication.activity.useractivity.StudentActivity
import org.hse.demoapplication.activity.useractivity.TeacherActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentScheduleButton.setOnClickListener {
            startActivity(Intent(this, StudentActivity::class.java))
        }

        teacherScheduleButton.setOnClickListener {
            startActivity(Intent(this, TeacherActivity::class.java))
        }
        preferenceButton.setOnClickListener {
            startActivity(Intent(this, PreferenceActivity::class.java))
        }
    }
}