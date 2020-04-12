package dh.shin.samsungalarm.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.StopWatchContent


class StopWatchAdapter(
    private val context: Context,
    private var stopContentList : ArrayList<StopWatchContent>
) :

    RecyclerView.Adapter<StopWatchAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.stopwatch_content, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return stopContentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(stopContentList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stopIndex = itemView.findViewById<TextView>(R.id.recycler_stop_index_text)
        val stopTime = itemView.findViewById<TextView>(R.id.recycler_stop_time_text)

        fun bind(stopContentItem: StopWatchContent) {
            stopIndex.text = stopContentItem.stopWatchIndex
            stopTime.text = stopContentItem.stopWatchTime
        }
    }
}
