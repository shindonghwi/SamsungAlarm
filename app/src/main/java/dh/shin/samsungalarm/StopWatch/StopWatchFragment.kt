package dh.shin.samsungalarm.StopWatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dh.shin.samsungalarm.Adapter.AlarmAdapter
import dh.shin.samsungalarm.Adapter.StopWatchAdapter
import dh.shin.samsungalarm.R
import dh.shin.samsungalarm.RecyclerViewItem.StopWatchContent
import kotlinx.android.synthetic.main.fragment_alarm.*
import kotlinx.android.synthetic.main.fragment_stop_watch.*
import kotlinx.coroutines.*

class StopWatchFragment : Fragment() {

    // 알람 데이터 리스트가 담기는 공간
    var stopWatchList = ArrayList<StopWatchContent>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stop_watch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 레이아웃 매니저 - 리사이클러뷰 연결
        stop_watch_recyclerView.layoutManager = LinearLayoutManager(context)

        // 리사이클러뷰 - 어댑터 연결
        stop_watch_recyclerView.adapter = StopWatchAdapter(this.requireContext(), stopWatchList)
    }

    override fun onResume() {
        super.onResume()

        // 화면을 옮겨다닐때 흐르고 있던 시간을 기준으로 텍스트를 초기화 시켜주는 함수
        timeSetting()

        // 시간이 흐르는 중
        if (stopWatchFlag) {

            // 시작버튼이 없고, (중지, 구간기록) 버튼이 보인다.
            stop_watch_start_btn.visibility = View.GONE

            stop_watch_left_btn.visibility = View.VISIBLE
            stop_watch_left_btn.text = "중지"

            stop_watch_right_btn.visibility = View.VISIBLE
            stop_watch_right_btn.text = "구간기록"
        }

        // 시작버튼을 눌렀을때 시간이 흐르게 작동시킨다.
        // 스톱워치 작동여부 stopWatchFlag를 활성화 시킨다.
        stop_watch_start_btn.setOnClickListener {

            stop_watch_start_btn.visibility = View.INVISIBLE

            stop_watch_left_btn.visibility = View.VISIBLE
            stop_watch_left_btn.text = "중지"

            stop_watch_right_btn.visibility = View.VISIBLE
            stop_watch_right_btn.text = "구간기록"

            // 스톱워치 flag 상태가 true이면 시간이 흐르는 중
            stopWatchFlag = true

            // 시간이 흐르는 방식
            GlobalScope.launch(Dispatchers.Default) {
                while (stopWatchFlag) {
                    delay(10)
                    stopWatchStart()
                }
            }

        }

        // 중지 버튼, 계속 버튼 눌렀을때
        stop_watch_left_btn.setOnClickListener {

            // 중지 버튼을 눌렀을때
            // 1. (중지, 구간기록) -> (계속, 초기화) 글자로 변경한다.
            // 2. 시간을 정지시킨다.
            if (stop_watch_left_btn.text == "중지") {
                stop_watch_left_btn.text = "계속"
                stop_watch_right_btn.text = "초기화"

                /** 2. 시간을 정지시키는 코드 추가해야함. */
                stopWatchFlag = false

            }

            // 계속 버튼을 눌렀을때
            // 1. (계속, 초기화) -> (중지, 구간기록) 글자로 변경한다.
            // 2. 시간을 다시 흐르게 한다.
            else if (stop_watch_left_btn.text == "계속") {
                stop_watch_left_btn.text = "중지"
                stop_watch_right_btn.text = "구간기록"
                /** 2. 시간을 다시 흐르게하는 코드 추가해야함. */
                stopWatchFlag = true
                timeSetting()
                GlobalScope.launch(Dispatchers.Default) {
                    while (stopWatchFlag) {
                        delay(10)
                        stopWatchStart()
                    }
                }
            }
        }

        // 구간기록 버튼, 초기화 버튼 눌렀을때
        stop_watch_right_btn.setOnClickListener {

            // 구간기록 버튼을 눌렀을때
            // 현재 시간을 리사이클러뷰에 컨텐츠를 추가한다.
            if (stop_watch_right_btn.text == "구간기록") {

                stopWatchList.add(
                    0,
                    StopWatchContent(
                        stopWatchList.size.toString(),
                        minute_text.text.toString() + ":" +
                                second_text.text.toString() + "." +
                                milli_text.text.toString()
                    )
                )

                stop_watch_recyclerView.adapter =
                    StopWatchAdapter(this.requireContext(), stopWatchList)

                GlobalScope.launch(Dispatchers.Default) {
                    GlobalScope.launch(Dispatchers.Main) {
                        (stop_watch_recyclerView.adapter as StopWatchAdapter).notifyDataSetChanged()
                    }
                }

                Log.d("Sdf", stopWatchList.size.toString())

                if (stopWatchList.size > 0) {
                    stop_watch_recycler_first.visibility = View.VISIBLE
                } else {
                    stop_watch_recycler_first.visibility = View.GONE
                }

            }

            // 초기화 버튼을 눌렀을때
            // 1. 다시 시작버튼이 생성되고, 시작버튼의 왼쪽, 오른쪽 버튼을 안보이게 처리한다.
            // 2. 시간을 초기화 시킨다.
            else if (stop_watch_right_btn.text == "초기화") {
                stop_watch_start_btn.visibility = View.VISIBLE
                stop_watch_left_btn.visibility = View.GONE
                stop_watch_right_btn.visibility = View.GONE

                // 시간 텍스트 초기화
                minute_text.text = getString(R.string.time_init)
                second_text.text = getString(R.string.time_init)
                milli_text.text = getString(R.string.time_init)

                // 시간 초기화
                minuteTime = 0
                secondTime = 0
                milliTime = 0

                // 구간기록 초기화
                stopWatchList.clear()
                stop_watch_recyclerView.adapter =
                    StopWatchAdapter(this.requireContext(), stopWatchList)

                GlobalScope.launch(Dispatchers.Default) {
                    GlobalScope.launch(Dispatchers.Main) {
                        (stop_watch_recyclerView.adapter as StopWatchAdapter).notifyDataSetChanged()
                    }
                }

                stop_watch_recycler_first.visibility = View.GONE

            }

        }


    }

    // 화면을 옮겨다닐때 흐르고 있던 시간을 기준으로 텍스트를 초기화 시켜주는 함수
    fun timeSetting() {
        if (minuteTime < 10) {
            try {
                minute_text.text = "0$minuteTime"
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        } else {
            try {
                minute_text.text = minuteTime.toString()
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        }

        if (secondTime < 10) {
            try {
                second_text.text = "0$secondTime"
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        } else {
            try {
                second_text.text = secondTime.toString()
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        }
        if (milliTime < 10) {
            try {
                milli_text.text = "0$milliTime"
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        } else {
            try {
                milli_text.text = milliTime.toString()
            } catch (e: Exception) {
                Log.d("stopWatch", "fragment close")
            }
        }
    }

    // 스톱워치 시간 흐르게 하는 함수
    fun stopWatchStart() {
        milliTime++

        if (milliTime >= 100) {
            milliTime = 0
            secondTime++

            if (secondTime >= 60) {
                secondTime = 0
                try {
                    try {
                        second_text.text = "0$secondTime"
                    } catch (e: Exception) {
                        Log.d("stopWatch", "fragment close")
                    }
                } catch (e: Exception) {
                    Log.d("stopWatch", "fragment close")
                }
                minuteTime++

                GlobalScope.launch(Dispatchers.Main) {
                    if (minuteTime < 10) {
                        try {
                            minute_text.text = "0$minuteTime"
                        } catch (e: Exception) {
                            Log.d("stopWatch", "fragment close")
                        }
                    } else {
                        try {
                            minute_text.text = minuteTime.toString()
                        } catch (e: Exception) {
                            Log.d("stopWatch", "fragment close")
                        }
                    }
                }

            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    if (secondTime < 10) {
                        try {
                            second_text.text = "0$secondTime"
                        } catch (e: Exception) {
                            Log.d("stopWatch", "fragment close")
                        }
                    } else {
                        try {
                            second_text.text = secondTime.toString()
                        } catch (e: Exception) {
                            Log.d("stopWatch", "fragment close")
                        }
                    }
                }
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                if (milliTime < 10) {
                    try {
                        milli_text.text = "0$milliTime"
                    } catch (e: Exception) {
                        Log.d("stopWatch", "fragment close")
                    }
                } else {
                    try {
                        milli_text.text = milliTime.toString()
                    } catch (e: Exception) {
                        Log.d("stopWatch", "fragment close")
                    }
                }
            }
        }

    }

    companion object {
        var stopWatchFlag = false
        var milliTime = 0
        var minuteTime = 0
        var secondTime = 0
    }

}