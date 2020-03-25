package dh.shin.samsungalarm.Adapter

import android.content.Context
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
    val context: Context,
    val alarm_list: ArrayList<AlarmContent>,
    var selected_flag: HashMap<Int, Boolean>,
    val delete_mode: Boolean
) :

    RecyclerView.Adapter<AlarmAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.alarm_content_item, parent, false)

        // 삭제 모드가 아니라면, 삭제체크박스 off, 알람스위치 on
        if (delete_mode == false) {
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
        return alarm_list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(alarm_list[position])

        holder.alarm_sw.isChecked = selected_flag.get(position) != false

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val is_am = itemView.findViewById<TextView>(R.id.recycler_is_am_text)
        val time =
            itemView.findViewById<TextView>(R.id.recycler_alarm_time_text)
        val day = itemView.findViewById<TextView>(R.id.recycler_alarm_day_text)
        val alarm_sw =
            itemView.findViewById<Switch>(R.id.recycler_alarm_switch)

        fun bind(alarm_content: AlarmContent) {
            is_am.text = alarm_content.is_am
            time.text = alarm_content.time
            day.text = alarm_content.day

            alarm_sw.setOnClickListener(View.OnClickListener {
                val pos = adapterPosition
                if (selected_flag.get(pos) == false) {
                    selected_flag.put(pos, true)
                } else {
                    selected_flag.put(pos, false)
                }
            })

        }
    }
}
