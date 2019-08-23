package sm.animation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sm.animation.R
import sm.animation.model.Boxes

class BoxAdapter(itemList: List<Boxes>) : RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    private val list: List<Boxes> = listOf(itemList.last()) + itemList + listOf(itemList.first())

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_boxes, parent, false)
        return BoxViewHolder(view)
    }

    override fun getItemCount() = list.size

    inner class BoxViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {

        }
    }
}