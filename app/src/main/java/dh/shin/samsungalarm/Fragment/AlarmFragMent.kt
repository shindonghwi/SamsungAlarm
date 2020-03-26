import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.util.SparseBooleanArray
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dh.shin.samsungalarm.Adapter.AlarmAdapter
import dh.shin.samsungalarm.AlarmAddActivity
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.AlarmContent
import dh.shin.samsungalarm.Util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alarm_content_item.*
import kotlinx.android.synthetic.main.fragment_alarm.*


class AlarmFragment : Fragment() {

    // 알람을 보여주는 형태가 삭제모드인가 아닌가를 나타내는 변수
    private var deleteMode: Boolean = false

//    private val ADD_ACTIVITY_RESULT_CODE = 1

    // 프리퍼런스 유틸 생성 ( 알람프래그먼트에서는 저장된 알람의 정보를 가져오기 위해 사용 할 예정)
    private lateinit var prefsUtil : PreferenceUtil

    // 알람 데이터 리스트가 담기는 공간
    var alarmList = ArrayList<AlarmContent>()

    // 알람의 on/off 상태를 가지고 있는 리스트
    var alarmSelectedFlagList = SparseBooleanArray()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 프리퍼런스 유틸 생성
        prefsUtil = PreferenceUtil(this.requireContext(), "alarmShared")

        // 레이아웃 매니저 - 리사이클러뷰 연결
        alarm_recyclerView.layoutManager = LinearLayoutManager(context)

        // 리사이클러뷰 - 어댑터 연결
        alarm_recyclerView.adapter =
            AlarmAdapter(this.requireContext(), alarmList, alarmSelectedFlagList, deleteMode)

    }

    override fun onResume() {
        super.onResume()

        // 알람 추가하는 화면으로 이동
        add_alarm_image_btn.setOnClickListener {
            val intent = Intent(context, AlarmAddActivity::class.java)
            startActivity(intent)
        }

        val popUpListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.alarm_setting_image_btn -> {
                    showPopup(view, activity)
                }
            }
        }

        alarm_setting_image_btn.setOnClickListener(popUpListener)
    }

    private fun showPopup(view: View, alarmActivity: FragmentActivity?) {
        val popup: PopupMenu?
        popup = PopupMenu(activity, view)
        popup.inflate(R.menu.alarm_setting_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                // 메뉴 - 삭제
                R.id.menu_delete -> {

                    // 전체(삭제) 버튼이 활성화 되고
                    // 알람 추가 및 설정 및 설정 메뉴가 사라진다.
                    // 아래 하단 메뉴바 또한 사라지게된다.

                    // 알람 삭제 모드로 변경
                    deleteMode = true

                    // 삭제 버튼 클릭 보이게 만듦
                    alarm_recyclerView.adapter = AlarmAdapter(
                        this.requireContext(),
                        alarmList,
                        alarmSelectedFlagList,
                        deleteMode
                    )

                    // 전체 삭제모드 보이게 만듦
                    delete_mode_l_layout.visibility = View.VISIBLE

                    // 알람 추가 버튼 안보이게 처리
                    add_alarm_image_btn.visibility = View.GONE

                    // 설정 메뉴 안보이게 처리
                    alarm_setting_image_btn.visibility = View.GONE

                    // 하단 메뉴바 안보이게 처리
                    if (alarmActivity != null) {
                        alarmActivity.findViewById<BottomNavigationView>(R.id.navigationView)
                            .visibility = View.INVISIBLE
                    }

                    // 삭제모드로 변경이 되었으니 어댑터에 새로 반영시켜준다.
                    (alarm_recyclerView.adapter as AlarmAdapter).notifyDataSetChanged()
                }

                // 메뉴 - 설정
                R.id.menu_setting -> {
                    Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()
                }

                // 메뉴 - 문의하기
                R.id.menu_inquiry -> {
                    Toast.makeText(activity, item.title, Toast.LENGTH_SHORT).show()
                }
            }

            true
        })

        popup.show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                when (requestCode) {
//                    // 요청코드가 알람추가 화면으로 이동하는 것(=1) 이라면
//                    ADD_ACTIVITY_RESULT_CODE -> {
//                        alarmList.add(AlarmContent("오전", "5:00", "3월 16일 (월)"))
//                        alarmSelectedFlagList.put(
//                            alarmList.size - 1,
//                            data.getBooleanExtra("alarm_switch_mode", false)
//                        )
//                        (alarm_recyclerView.adapter as AlarmAdapter).notifyDataSetChanged()
//                    }
//                }
//            }
//        }
//    }

}

