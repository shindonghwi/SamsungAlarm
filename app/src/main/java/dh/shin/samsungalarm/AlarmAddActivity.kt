package dh.shin.samsungalarm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_alarm_add.*

class AlarmAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_add)
    }

    override fun onResume() {
        super.onResume()

        // 취소 버튼을 눌렀을때 이전 화면으로 돌아간다.
        cancel_text.setOnClickListener {
            finish()
        }

        // 확인 버튼을 눌렀을때 설정한 알람이 등록된다.
        check_text.setOnClickListener {
            val intent = Intent()
            intent.putExtra("is_am","오전")
            intent.putExtra("alarm_day","3월 16일 (월)")
            intent.putExtra("alarm_time","5:00")
            intent.putExtra("alarm_switch_mode",true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }
}
