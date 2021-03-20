package org.hse.demoapplication.activity.scheduleactivity

import org.hse.demoapplication.model.schedule.ScheduleItem

interface OnItemClick {
    fun onClick(data: ScheduleItem);
}