package sm.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.toolbar.*
import sm.animation.adapter.BoxAdapter
import sm.animation.adapter.InfiniteRotationView
import sm.animation.model.Boxes


class MainActivity : AppCompatActivity() {
    val translateAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
    @BindView(R.id.rv_boxes)
    internal lateinit var rotationView: InfiniteRotationView
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        handler = Handler()
        val items = getDummyData()
        rotationView.setAdapter(BoxAdapter(items))
        rotationView.autoScroll(items.size, 3000)
        rotationView.getCurrentValue().observe(this, Observer {
            val vh = rotationView.recyclerView.findViewHolderForAdapterPosition(it)
            if (vh != null) {
                val view = (vh as BoxAdapter.BoxViewHolder).flipLayout
                val rotate = ObjectAnimator.ofFloat(view,"rotationX",0f,130f)
                rotate.repeatMode = ValueAnimator.REVERSE
                rotate.repeatCount = 1
                val translate = ObjectAnimator.ofFloat(view,"translationY",view.x-100f,-(view.x+180f))
                translate.repeatMode = ValueAnimator.REVERSE
                translate.repeatCount = 1
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(rotate, translate)
                animatorSet.duration = 2300
                animatorSet.start()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        rotationView.stopAutoScroll()
    }

    fun getDummyData(): List<Boxes> {
        val list = mutableListOf<Boxes>()
        list.add(
            Boxes(
                "", "MENS SHOE", "nike air max", "270",
                "$150", "4.5"
            )
        )
        list.add(
            list[0]
        )
        list.add(
            list[0]
        )
        list.add(
            list[0]
        )
        list.add(
            list[0]
        )
        list.add(
            list[0]
        )
        return list
    }
}
