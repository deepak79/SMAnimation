package sm.animation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import sm.animation.R
import sm.animation.model.Boxes


class BoxAdapter(itemList: List<Boxes>) : RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    private val list: List<Boxes> = listOf(itemList.last()) + itemList + listOf(itemList.first())

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_boxes, parent, false)
        return BoxViewHolder(view)
    }

    override fun getItemCount() = list.size

    class BoxViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.flipLayout)
        lateinit var flipLayout: ImageView

        init {
            ButterKnife.bind(this, view)
        }
    }

    override fun onViewDetachedFromWindow(holder: BoxViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.flipLayout.clearAnimation()
    }
}