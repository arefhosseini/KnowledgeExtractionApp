package ir.fearefull.knowledgeextractionapp.ui.relation

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.databinding.ItemRelationBinding
import ir.fearefull.knowledgeextractionapp.model.Relation

class RelationListAdapter: RecyclerView.Adapter<RelationListAdapter.ViewHolder>() {
    private lateinit var relationList:List<Relation>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationListAdapter.ViewHolder {
        val binding: ItemRelationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_relation, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelationListAdapter.ViewHolder, position: Int) {
        holder.bind(relationList[position])
    }

    override fun getItemCount(): Int {
        return if(::relationList.isInitialized) relationList.size else 0
    }

    fun updateRelationList(relationList:List<Relation>){
        this.relationList = relationList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRelationBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = RelationViewModel()


        fun bind(relation: Relation){
            viewModel.bind(relation)
            binding.viewModel = viewModel
        }
    }
}