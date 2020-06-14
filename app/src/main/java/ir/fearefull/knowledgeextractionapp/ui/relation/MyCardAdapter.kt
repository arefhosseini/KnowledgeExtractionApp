package ir.fearefull.knowledgeextractionapp.ui.relation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard
import ir.fearefull.knowledgeextractionapp.databinding.MyCardBinding
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewHolder
import java.util.*

class MyCardAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val itemList: MutableList<MyCard>
    lateinit var listener: MyCardAdapterListener

    init {
        itemList = ArrayList<MyCard>()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: MyCardBinding = MyCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyCardViewHolder(binding)
    }

    fun addItems(alarmTitleItemList: List<MyCard>?) {
        this.itemList.addAll(alarmTitleItemList!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        itemList.clear()
    }

    interface MyCardAdapterListener {
        fun onMyCardItemClick(item: MyCard?)
    }

    inner class MyCardViewHolder(private val binding: MyCardBinding) :
        BaseViewHolder(binding.root),
        MyCardViewModel.MyCardViewModelListener {
        private var viewModel: MyCardViewModel? = null
        override fun onBind(position: Int) {
            val item: MyCard = itemList[position]
            viewModel = MyCardViewModel(item, this)
            binding.viewModel = viewModel
            binding.executePendingBindings()
            item.binding = binding
        }

        override fun onItemClick(item: MyCard?) {
            listener.onMyCardItemClick(item)
        }

    }
}
