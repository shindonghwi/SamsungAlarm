package dh.shin.samsungalarm

import AlarmFragment
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dh.shin.samsungalarm.StopWatch.StopWatchFragment
import dh.shin.samsungalarm.Timer.TimerFragment
import dh.shin.samsungalarm.WorldTimeFragment.WorldTimeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val alarmFragment = AlarmFragment() // 알람 프래그먼트
    private val worldTimeFragment =
        WorldTimeFragment() // 세계시간 보여주는 프래그먼트
    private val stopWatchFragment =
        StopWatchFragment() // 스톱워치 기능이 있는 프래그먼트
    private val timerFragment = TimerFragment() // 타이머 기능이 있는 프래그먼트
    private var backBtnCheck = false // 뒤로가기 버튼 클릭 여부 체크

    private lateinit var fragmentManager: FragmentManager // 프래그먼트 관리자

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 프래그먼트 관리자 생성
        fragmentManager = supportFragmentManager

        // 프래그먼트 교체하기 ( 화면 하단의 메뉴를 통해 화면전환 기능 )
        replaceFragment(alarmFragment)

    }

    override fun onResume() {
        super.onResume()
        /** 바텀네비게이션 버튼 눌렀을때 해당 화면으로 교체*/
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_alarm -> {
                    replaceFragment(alarmFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_world_time -> {
                    replaceFragment(worldTimeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_stop_watch -> {
                    replaceFragment(stopWatchFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_timer -> {
                    replaceFragment(timerFragment)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

    // 프래그먼트 불러오는 메서드
    private fun replaceFragment(fragment: Fragment) {

        // load fragment
        val transaction = fragmentManager.beginTransaction()

        // frame_layout 부분을 화면 하단에 선택한 아이템의 프래그먼트로 교체한다.
        transaction.replace(R.id.frame_layout, fragment)

        // 변경사항을 적용
        transaction.commit()
    }

    /**
     * 뒤로가기 버튼 클릭을 했을때 호출되는 메서드
     * 2번 연속으로 클릭시 앱 종료가 된다.
     * */
    override fun onBackPressed() {
        if (backBtnCheck) {
            super.onBackPressed()
            return
        }

        // 액티비티를 바로 종료 시킬 수 있는 상태로 만든다 -> true
        this.backBtnCheck = true
        Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()

        // 2초가 지나면 액티비티를 바로 종료 시킬 수 없다.
        Handler().postDelayed({ backBtnCheck = false }, 2000)
    }

}
