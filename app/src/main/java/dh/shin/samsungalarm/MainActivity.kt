package dh.shin.samsungalarm

import AlarmFragment
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dh.shin.samsungalarm.Fragment.StopWatchFragment
import dh.shin.samsungalarm.Fragment.TimerFragment
import dh.shin.samsungalarm.Fragment.WorldTimeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val alarm_fragment = AlarmFragment()
    val world_time_fragment = WorldTimeFragment()
    val stop_watch_fragment = StopWatchFragment()
    val timer_fragment = TimerFragment()

    var doubleBackToExitPressedOnce = false

    lateinit var back_listener : onKeyBackPressedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(alarm_fragment)

        back_listener.onBack()
    }

    override fun onResume() {
        super.onResume()
        /** 바텀네비게이션 버튼 눌렀을때 해당 화면으로 교체*/
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_alarm -> {
                    loadFragment(alarm_fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_world_time -> {
                    loadFragment(world_time_fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_stop_watch -> {
                    loadFragment(stop_watch_fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_timer -> {
                    loadFragment(timer_fragment)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

    // 프래그먼트 불러오는 메서드
    private fun loadFragment(fragment: Fragment) {

        // load fragment
        val transaction = supportFragmentManager.beginTransaction()

        // frame_layout 부분을 fragment로 갈아끼우겠다.
        transaction.replace(R.id.frame_layout, fragment)

        // 변경사항을 적용
        transaction.commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        // 액티비티를 바로 종료 시킬 수 있는 상태로 만든다 -> true
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()

        // 2초가 지나면 액티비티를 바로 종료 시킬 수 없다.
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    public interface onKeyBackPressedListener{
        fun onBack()
    }

    public fun setOnKeyBackPressedListener(listener : onKeyBackPressedListener){
        back_listener = listener
    }


}
