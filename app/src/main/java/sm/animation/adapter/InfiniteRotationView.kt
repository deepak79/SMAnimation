package sm.animation.adapter


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
import java.util.concurrent.TimeUnit


class InfiniteRotationView(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    @BindView(R.id.box_recyclerview)
    internal lateinit var recyclerView: RecyclerView
    private val layoutManager: LinearLayoutManager
    private lateinit var onScrollListener: OnScrollListener
    var currentPosition = MutableLiveData<Int>()

    private var dispose: Disposable? = null

    init {
        View.inflate(context, R.layout.view_infinite_rotation, this)
        ButterKnife.bind(this)
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }

    fun getCurrentValue(): LiveData<Int> {
        return currentPosition
    }

    fun setAdapter(adapter: BoxAdapter) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

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
                recyclerView.addOnScrollListener(onScrollListener)
                recyclerView.scrollToPosition(1)
            }
    }

    fun autoScroll(listSize: Int, intervalInMillis: Long) {
        dispose?.let {
            if (!it.isDisposed) return
        }
        dispose = Flowable.interval(intervalInMillis, TimeUnit.MILLISECONDS)
            .map { it % listSize + 1 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentPosition.postValue(it.toInt() + 1)
                recyclerView.smoothScrollToPosition(it.toInt() + 1)
            }
    }

    fun stopAutoScroll() {
        dispose?.let(Disposable::dispose)
    }

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