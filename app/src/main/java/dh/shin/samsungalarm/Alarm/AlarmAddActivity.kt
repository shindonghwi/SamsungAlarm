package dh.shin.samsungalarm.Alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.AlarmContent
import dh.shin.samsungalarm.Util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_alarm_add.*
import org.json.JSONArray

class AlarmAddActivity : AppCompatActivity() {

    private lateinit var prefsUtil : PreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_add)

        // 알람 저장을 위해서 쉐어드 프리퍼런스 유틸 생성
        prefsUtil = PreferenceUtil(this, "alarmShared")

    }

    override fun onResume() {
        super.onResume()

        // 취소 버튼을 눌렀을때 이전 화면으로 돌아간다.
        cancel_text.setOnClickListener {
            finish()
        }

        // 확인 버튼을 눌렀을때 설정한 알람이 등록된다.
        check_text.setOnClickListener {

            var jsonArray = JSONArray()

            var json = Gson().toJson(AlarmContent("오전","3월 16일 (월)", "5:00"))
            prefsUtil.putData(Pair("alarmData",json))

            finish()
        }


    }
}
