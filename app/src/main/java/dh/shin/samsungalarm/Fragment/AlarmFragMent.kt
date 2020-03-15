import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dh.shin.samsungalarm.Adapter.AlarmAdapter
import dh.shin.samsungalarm.AlarmAddActivity
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.AlarmContent
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment() {

    // 알람 데이터 리스트가 담기는 공간
    var alarm_list = ArrayList<AlarmContent>()

    // 알람의 on/off 상태를 가지고 있는 리스트
    var selected_list = HashMap<Int, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 알람 데이터 및 on/off 상태 넣기
        for (i in 0..10) {
            alarm_list.add(AlarmContent("오전", "5:00", "3월 16일 (월)"))
            selected_list.put(i, false)
        }


        // 레이아웃 매니저 - 리사이클러뷰 연결
        alarm_recyclerView.layoutManager = LinearLayoutManager(context)

        // 리사이클러뷰 - 어댑터 연결
        alarm_recyclerView.adapter = AlarmAdapter(this.requireContext(), alarm_list, selected_list)

    }

    override fun onResume() {
        super.onResume()

        add_alarm_image_btn.setOnClickListener {
            val intent = Intent(context,AlarmAddActivity::class.java)
            startActivity(intent)
        }

    }

}

