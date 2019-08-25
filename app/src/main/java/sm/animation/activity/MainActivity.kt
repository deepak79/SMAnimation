package sm.animation.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import sm.animation.R
import sm.animation.adapter.BoxAdapter
import sm.animation.customviews.CustomRecyclerView
import sm.animation.model.Boxes


class MainActivity : AppCompatActivity() {
    @BindView(R.id.custom_rv)
    lateinit var mCustomRecyclerView: CustomRecyclerView//custom recyclerview
    private var mHalfWidth = 0f//half width
    lateinit var mAnim_lToR_shoe_type: ObjectAnimator//animation left to right for shoe type
    lateinit var mAnim_lToR_shoe_name: ObjectAnimator//animation left to right for shoe name
    lateinit var mAnim_lToR_shoe_subtitle: ObjectAnimator//animation left to right for shoe subtitle
    lateinit var mAnim_lToR_shoe_price: ObjectAnimator//animation left to right for shoe price
    lateinit var mAnim_lToR_shoe_rating: ObjectAnimator//animation left to right for shoe rating
    private val mTranslationProperty = "translationX"

    /*
    * DOWNLOAD APK
    * https://drive.google.com/file/d/1d2sbrdoo_Db8PlQPzcH0LlkNIuW8clno/view?usp=sharing
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inject views using butterknife
        ButterKnife.bind(this)

        //Set custom toolbar
        setSupportActionBar(toolbar)
        mHalfWidth = getHalfWidth()

        initializeAnimations()

        //Set custom recyclerview adapter and start autoscrolling
        val items = getDummyData()
        mCustomRecyclerView.setAdapter(BoxAdapter(items))
        mCustomRecyclerView.autoScroll(items.size, 3000)

        mCustomRecyclerView.getCurrentValue().observe(this, Observer {
            val vh = mCustomRecyclerView.rv_boxes.findViewHolderForAdapterPosition(it)
            if (vh != null) {
                val currentItem = getDummyData()[it - 2]
                val view = (vh as BoxAdapter.BoxViewHolder).img_shoe_top
                vh.img_shoe.setImageDrawable(currentItem.image)
                val rotate = ObjectAnimator.ofFloat(view, "rotationX", 0f, 130f).apply {
                    repeatMode = ValueAnimator.REVERSE
                    repeatCount = 1
                }
                val translate = ObjectAnimator.ofFloat(view, "translationY", view.x - 100f, -(view.x + 180f)).apply {
                    repeatCount = 1
                    repeatMode = ValueAnimator.REVERSE
                }
                tv_shoe_type.text = currentItem.shoe_type
                tv_shoe_name.text = currentItem.shoe_name
                tv_shoe_subtitle.text = currentItem.shoe_subtitle
                tv_shoe_price.text = currentItem.shoe_price
                tv_shoe_rating.text = currentItem.shoe_rating
                startAnimation()
                AnimatorSet().apply {
                    playTogether(rotate, translate)
                    duration = 2300
                    start()
                }
            }
        })
    }

    /*
    * Start textview animatons together
    * */
    private fun startAnimation() {
        mAnim_lToR_shoe_type.start()
        mAnim_lToR_shoe_name.start()
        mAnim_lToR_shoe_subtitle.start()
        mAnim_lToR_shoe_price.start()
        mAnim_lToR_shoe_rating.start()
    }

    /*
    * Initialize animations
    * */
    private fun initializeAnimations() {
        mAnim_lToR_shoe_type = ObjectAnimator.ofFloat(tv_shoe_type, mTranslationProperty, mHalfWidth, 0f).apply {
            duration = 750
        }
        mAnim_lToR_shoe_name = ObjectAnimator.ofFloat(tv_shoe_name, mTranslationProperty, mHalfWidth, 0f).apply {
            duration = 750
        }
        mAnim_lToR_shoe_subtitle =
            ObjectAnimator.ofFloat(tv_shoe_subtitle, mTranslationProperty, mHalfWidth, 0f).apply {
                duration = 750
            }
        mAnim_lToR_shoe_price = ObjectAnimator.ofFloat(tv_shoe_price, mTranslationProperty, mHalfWidth, 0f).apply {
            duration = 750
        }
        mAnim_lToR_shoe_rating = ObjectAnimator.ofFloat(tv_shoe_rating, mTranslationProperty, mHalfWidth, 0f).apply {
            duration = 750
        }
    }

    /*
    * Get half width of total screen for animation
    * */
    private fun getHalfWidth(): Float {
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        return point.x.toFloat()
    }

    /*
    * Stop autoscrolling onDestroy
    * */
    override fun onDestroy() {
        super.onDestroy()
        mCustomRecyclerView.stopAutoScroll()
    }

    /*
    * Generate dummy data for recyclerview adapter
    * returns List<Boxes>
    * */
    fun getDummyData(): List<Boxes> {
        val list = mutableListOf<Boxes>()
        list.add(
            Boxes(
                ContextCompat.getDrawable(this, R.drawable.ic_shoes1)!!, "MENS SHOE", "Nike Air Max", "270",
                "$150", "4.9"
            )
        )
        list.add(
            Boxes(
                ContextCompat.getDrawable(this, R.drawable.ic_shoes2)!!,
                "MENS SHOE",
                "Nike React Presto",
                "Rabid Panda",
                "$250",
                "4.1"
            )
        )
        list.add(
            Boxes(
                ContextCompat.getDrawable(this, R.drawable.ic_shoes3)!!, "MENS SHOE", "Nike Air VaporMax", "Flyknit 2",
                "$299", "4.3"
            )
        )
        list.add(
            Boxes(
                ContextCompat.getDrawable(this, R.drawable.ic_shoes4)!!, "MENS SHOE", "Nike Viper", "Skyskit",
                "$350", "4.2"
            )
        )
        list.add(
            Boxes(
                ContextCompat.getDrawable(this, R.drawable.ic_shoes5)!!, "MENS SHOE", "Nike Venom", "xLord",
                "$280", "4.7"
            )
        )
        return list
    }
}
