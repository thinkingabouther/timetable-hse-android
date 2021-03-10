package org.hse.demoapplication.activity.ScheduleActivity

import org.hse.demoapplication.model.schedule.ScheduleItem

interface OnItemClick {
    fun onClick(data: ScheduleItem);
}