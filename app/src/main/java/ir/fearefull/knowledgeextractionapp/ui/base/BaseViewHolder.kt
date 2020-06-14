package ir.fearefull.knowledgeextractionapp.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard

abstract class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    abstract fun onBind(position: Int)
    abstract fun onItemClick(item: MyCard?)
}