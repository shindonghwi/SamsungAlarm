package dh.shin.samsungalarm.Timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dh.shin.samsungalarm.R
import kotlinx.android.synthetic.main.fragment_timer.*

class TimerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onResume() {
        super.onResume()


        timer_start_btn.setOnClickListener {

        }

    }
}