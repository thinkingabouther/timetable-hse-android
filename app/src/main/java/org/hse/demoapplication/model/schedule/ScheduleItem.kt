package org.hse.demoapplication.model.schedule

import java.util.*

open class ScheduleItem {
    lateinit var start: String
    lateinit var end: String
    lateinit var type: String
    lateinit var name: String
    lateinit var place: String
    lateinit var teacher: String
    lateinit var dateStart : Date
}