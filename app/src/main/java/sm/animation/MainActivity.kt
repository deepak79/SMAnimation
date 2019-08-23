package sm.animation

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import sm.animation.adapter.BoxAdapter
import sm.animation.adapter.CenterLayoutManager


class MainActivity : AppCompatActivity() {
    var isFlipped = false
    val translateAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
    lateinit var adapter: BoxAdapter
    lateinit var lm: CenterLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        lm = CenterLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = BoxAdapter(this)
        rv_boxes.layoutManager = lm
        rv_boxes.adapter = adapter
//        val rotateAnimator = ObjectAnimator.ofFloat(flipLayout, "rotationX", 0f, 140f)
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(translateAnimator, rotateAnimator)
//        animatorSet.duration = 2000
//
//        val y = flipLayout.y
//        translateAnimator.addUpdateListener {
//            val t = translateAnimator.animatedValue as Float
//            flipLayout.translationY = -(y + t * 300)
//            flipLayout.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.front))
//        }
        cv_cart.setOnClickListener {
            lm.smoothScrollToPosition(rv_boxes, RecyclerView.State(), 4)
        }
    }
}
