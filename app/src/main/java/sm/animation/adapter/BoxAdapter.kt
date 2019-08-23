package sm.animation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sm.animation.R

class BoxAdapter(val context: Context) : RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_boxes, parent, false)
        return BoxViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {

    }

    inner class BoxViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {

        }
    }
}