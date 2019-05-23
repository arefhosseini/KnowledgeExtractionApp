package ir.fearefull.knowledgeextractionapp.ui.relation

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.databinding.ItemRelationBinding
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationResponse

class RelationListAdapter: RecyclerView.Adapter<RelationListAdapter.ViewHolder>() {
    private lateinit var relationResponseList:List<RelationResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationListAdapter.ViewHolder {
        val binding: ItemRelationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_relation, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelationListAdapter.ViewHolder, position: Int) {
        holder.bind(relationResponseList[position])
    }

    override fun getItemCount(): Int {
        return if(::relationResponseList.isInitialized) relationResponseList.size else 0
    }

    fun updateRelationList(relationResponseList:List<RelationResponse>){
        this.relationResponseList = relationResponseList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRelationBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = RelationViewModel()


        fun bind(relationResponse: RelationResponse){
            viewModel.bind(relationResponse)
            binding.viewModel = viewModel
        }
    }
}