package com.happy.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.happy.todo.weight.LevelCircularProgressBar
import android.widget.TextView
import com.happy.todo.weight.CircularProgressBar
import com.happy.todo.weight.MultipleProgressBar
import com.happy.todo.weight.StatisticsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /* PathCoustomView view = findViewById(R.id.path_coustom_view);
        view.setOnClickListener(v -> {
            view.startMove();
        });*/

        //setContentView1();
        //setContentView2();
        //  setContentView3();
        setContentView4()
    }

    var step4 = 0f
    private fun setContentView4() {
        setContentView(R.layout.activity_costom_view_d)
        val lcp = findViewById<LevelCircularProgressBar>(R.id.lcp)
        lcp.balance = 998
        lcp.progress = step4
        val b1 = findViewById<Button>(R.id.b1)
        val b2 = findViewById<Button>(R.id.b2)
        val tv = findViewById<TextView>(R.id.tv)
        b1.setOnClickListener { v: View? ->
            step4 -= 1f
            tv.text = step4.toString() + ""
            lcp.progress = step4
        }
        b2.setOnClickListener { v: View? ->
            step4 += 1f
            tv.text = step4.toString() + ""
            lcp.progress = step4
        }
    }

    var step1 = 100f
    private fun setContentView3() {
        val total = 500f
        setContentView(R.layout.activity_costom_view_c)
        val cp = findViewById<CircularProgressBar>(R.id.cp)
        cp.setProgress(step1 / total, (total - step1) / total)
        val b1 = findViewById<Button>(R.id.b1)
        val b2 = findViewById<Button>(R.id.b2)
        val tv = findViewById<TextView>(R.id.tv)
        b1.setOnClickListener { v: View? ->
            step1 -= 10f
            if (step1 < 1) step1 = 1f
            tv.text = step1.toString() + ""
            cp.setProgress(step1 / total, (total - step1) / total)
        }
        b2.setOnClickListener { v: View? ->
            step1 += 10f
            if (step1 > total) step1 = total
            tv.text = step1.toString() + ""
            cp.setProgress(step1 / total, (total - step1) / total)
        }
    }

    var step = 1
    private fun setContentView2() {
        setContentView(R.layout.activity_costom_view_b)
        val b1 = findViewById<Button>(R.id.b1)
        val b2 = findViewById<Button>(R.id.b2)
        val tv = findViewById<TextView>(R.id.tv)
        val mp = findViewById<MultipleProgressBar>(R.id.mp)
        b1.setOnClickListener { v: View? ->
            step--
            if (step < 1) step = 1
            tv.text = step.toString() + "X"
            mp.setProgress(step.toFloat())
        }
        b2.setOnClickListener { v: View? ->
            step++
            if (step > 125) step = 125
            tv.text = step.toString() + "X"
            mp.setProgress(step.toFloat())
        }
    }

    private fun setContentView1() {
        setContentView(R.layout.activity_costom_a)
        textView()
    }

    private fun textView() {
        val sv1 = findViewById<StatisticsView>(R.id.sv1)
        val sv2 = findViewById<StatisticsView>(R.id.sv2)
        val sv3 = findViewById<StatisticsView>(R.id.sv3)
        val sv4 = findViewById<StatisticsView>(R.id.sv4)
        val sv5 = findViewById<StatisticsView>(R.id.sv5)
        val sv6 = findViewById<StatisticsView>(R.id.sv6)
        sv1.random()
        sv2.random()
        sv3.random()
        sv4.random()
        sv5.random()
        sv6.random()
        sv1.setOnClickListener { l: View? -> }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_CODE = 12
    }
}