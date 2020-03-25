package dh.shin.samsungalarm.Adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.AlarmContent


class AlarmAdapter(
    private val context: Context,
    private val adpaterAlarmList: ArrayList<AlarmContent>,
    private var adpaterSelectedFlag: SparseBooleanArray,
    private val adpaterDeleteMode: Boolean
) :

    RecyclerView.Adapter<AlarmAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.alarm_content_item, parent, false)

        // 삭제 모드가 아니라면, 삭제체크박스 off, 알람스위치 on
        if (!adpaterDeleteMode) {
            view.findViewById<CheckBox>(R.id.recycler_delete_btn).visibility = View.GONE
            view.findViewById<Switch>(R.id.recycler_alarm_switch).visibility = View.VISIBLE
        }

        // 삭제 모드가 아니라면, 삭제체크박스 on, 알람스위치 off
        else {
            view.findViewById<CheckBox>(R.id.recycler_delete_btn).visibility = View.VISIBLE
            view.findViewById<Switch>(R.id.recycler_alarm_switch).visibility = View.GONE
        }

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return adpaterAlarmList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(adpaterAlarmList[position])

        holder.alarmSwitchMode.isChecked = adpaterSelectedFlag.get(position) != false

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val isAm = itemView.findViewById<TextView>(R.id.recycler_is_am_text)
        val alarmTime =
            itemView.findViewById<TextView>(R.id.recycler_alarm_time_text)
        val alarmDay = itemView.findViewById<TextView>(R.id.recycler_alarm_day_text)
        val alarmSwitchMode =
            itemView.findViewById<Switch>(R.id.recycler_alarm_switch)

        fun bind(alarm_content: AlarmContent) {
            isAm.text = alarm_content.is_am
            alarmTime.text = alarm_content.time
            alarmDay.text = alarm_content.day

            alarmSwitchMode.setOnClickListener(View.OnClickListener {
                val pos = adapterPosition
                if (adpaterSelectedFlag.get(pos) == false) {
                    adpaterSelectedFlag.put(pos, true)
                } else {
                    adpaterSelectedFlag.put(pos, false)
                }
            })

        }
    }
}
