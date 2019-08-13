package sm.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var flippingup: Animator
    lateinit var flippingdown: Animator
    var isFlipped = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flippingdown = AnimatorInflater.loadAnimator(this, R.animator.flippingdown)
        flippingup = AnimatorInflater.loadAnimator(this, R.animator.flippingup)
        btnFlip.setOnClickListener {
            //            if (!isFlipped) {
//                flippingup.setTarget(flipLayout)
//                flippingup.duration = 1000
//                flippingup.start()
//                isFlipped = true
//            } else {
//                flippingdown.setTarget(flipLayout)
//                flippingdown.duration = 1000
//                flippingdown.start()
//                isFlipped = false
//            }
            val rotation = Rotate3dAnimation(0f, 140f, 0f, 0f, 310.0f, true)
            rotation.duration = 1000
            rotation.fillAfter = true
            rotation.interpolator = AccelerateInterpolator()
            flipLayout.startAnimation(rotation)
        }
    }
}
