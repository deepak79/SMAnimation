package sm.animation.customviews


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import android.widget.RelativeLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import sm.animation.R
import sm.animation.adapter.BoxAdapter
import java.util.concurrent.TimeUnit

/*
* Custom recyclerview for autoscrolling
* */
class CustomRecyclerView(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    @BindView(R.id.rv_boxes)
    internal lateinit var rv_boxes: RecyclerView//recyclerview
    private val layoutManager: LinearLayoutManager//layout manager for recyclerview
    private lateinit var onScrollListener: OnScrollListener//scroll listener for auto scrolling
    private var currentPosition = MutableLiveData<Int>()//mutablelivedata to publish current position to mainactivity
    private var dispose: Disposable? = null//disposable to store subscription of flowable

    //init views
    init {
        View.inflate(context, R.layout.view_infinite_rotation, this)
        ButterKnife.bind(this)
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }

    /*
    * returns current value from recyclerview adapter
    * */
    fun getCurrentValue(): LiveData<Int> {
        return currentPosition
    }

    /*
    * set recyclerview adapter
    * */
    fun setAdapter(adapter: BoxAdapter) {
        rv_boxes.layoutManager = layoutManager
        rv_boxes.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_boxes)
        adapter.itemCount
            .takeIf { it > 1 }
            ?.apply {
                onScrollListener = OnScrollListener(
                    adapter.itemCount,
                    layoutManager
                ) {
                    if (it == RecyclerView.SCROLL_STATE_DRAGGING) {
                        dispose?.dispose()
                    }
                }
                rv_boxes.addOnScrollListener(onScrollListener)
                rv_boxes.scrollToPosition(1)
            }
    }

    /*
    * auto scroll to recyclerview
    * @param listSize = total size of the list
    * @param intervalInMillis = total delay for autoscrolling
    * */
    fun autoScroll(listSize: Int, intervalInMillis: Long) {
        dispose?.let {
            if (!it.isDisposed) return
        }
        dispose = Flowable.interval(intervalInMillis, TimeUnit.MILLISECONDS)
            .map { it % listSize + 1 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentPosition.postValue(it.toInt() + 1)
                rv_boxes.smoothScrollToPosition(it.toInt() + 1)
            }
    }


    /*
    * stop auto scroll
    * */
    fun stopAutoScroll() {
        dispose?.let(Disposable::dispose)
    }

    /*
    * Anonymous class of scroll listener to recyclerview
    * */
    class OnScrollListener(
        val itemCount: Int,
        val layoutManager: LinearLayoutManager,
        val stateChanged: (Int) -> Unit
    ) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()

            if (firstItemVisible > 0 && firstItemVisible % (itemCount - 1) == 0) {
                // When position reaches end of the list, it should go back to the beginning
                recyclerView.scrollToPosition(1)
            } else if (firstItemVisible == 0) {
                // When position reaches beginning of the list, it should go back to the end
                recyclerView.scrollToPosition(itemCount - 1)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            stateChanged(newState)
        }
    }
}