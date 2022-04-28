package com.happy.todo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.happy.todo.weight.CameraControlBar
import kotlinx.android.synthetic.main.activity_progressbar.*

/**
 * Author by Ouyangle, Date on 2022/4/11.
 * PS: Not easy to write code, please indicate.
 */
class ProgressBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressbar)

        view_ccb.setListener(object : CameraControlBar.OnControlBarClickListener{
            override fun onTopAction(view: View?) {
                Log.d(
                    "fafdafadfadfa",
                    "onTopAction"
                )

            }

            override fun onBottomAction(view: View?) {
                Log.d(
                    "fafdafadfadfa",
                    "onBottomAction"
                )

            }

            override fun onLeftAction(view: View?) {
                Log.d(
                    "fafdafadfadfa",
                    "onLeftAction"
                )
            }

            override fun onRightAction(view: View?) {
                Log.d(
                    "fafdafadfadfa",
                    "onRightAction"
                )
            }

        });
    }

}