package org.hse.demoapplication.activity.scheduleactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hse.demoapplication.R
import org.hse.demoapplication.model.schedule.ScheduleItem
import org.hse.demoapplication.model.schedule.ScheduleItemHeader
import java.util.ArrayList

class ItemAdapter(private val onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TYPE_ITEM = 0;
    val TYPE_HEADER = 1;
    var dataList = ArrayList<ScheduleItem>()

    class ViewHolder(view: View, context : Context, onItemClick: OnItemClick) : RecyclerView.ViewHolder(view) {
        private val startTextView : TextView = view.findViewById(R.id.startTextView)
        private val endTextView : TextView = view.findViewById(R.id.endTextView)
        private val typeTextView : TextView = view.findViewById(R.id.typeTextView)
        private val nameTextView : TextView = view.findViewById(R.id.nameTextView)
        private val placeTextView : TextView = view.findViewById(R.id.placeTextView)
        private val teacherTextView : TextView = view.findViewById(R.id.teacherTextView)

        fun bind(data : ScheduleItem) {
            startTextView.text = data.start
            endTextView.text = data.end
            typeTextView.text = data.type
            nameTextView.text = data.name
            placeTextView.text = data.place
            teacherTextView.text = data.teacher
        }
    }

    class ViewHolderHeader(view: View, context : Context, onItemClick: OnItemClick) : RecyclerView.ViewHolder(view) {
        private val titleTextView : TextView = view.findViewById(R.id.titleTextView)
        fun bind(data : ScheduleItemHeader) {
            titleTextView.text = data.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)

        if (viewType == TYPE_ITEM) {
            val contactView = inflater.inflate(R.layout.item_schedule, viewGroup, false)
            return ViewHolder(contactView, context, onItemClick)
        } else if (viewType == TYPE_HEADER) {
            val contactView = inflater.inflate(R.layout.item_header, viewGroup, false)
            return ViewHolderHeader(contactView, context, onItemClick)
        }
        throw IllegalArgumentException("Invalid view type")
    }

    override fun getItemViewType(position: Int): Int {
        val data = dataList[position]
        if (data is ScheduleItemHeader) return TYPE_HEADER
        return TYPE_ITEM
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if (viewHolder is ViewHolder) viewHolder.bind(data);
        else if (viewHolder is ViewHolderHeader) viewHolder.bind(data as ScheduleItemHeader)
    }

    override fun getItemCount() = dataList.size

}