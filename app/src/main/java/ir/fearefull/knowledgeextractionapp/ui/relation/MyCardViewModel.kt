package ir.fearefull.knowledgeextractionapp.ui.relation

import androidx.databinding.ObservableField
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard

class MyCardViewModel(
    private val item: MyCard,
    private val listener: MyCardViewModelListener
) {
    val name: ObservableField<String> = ObservableField()
    val coverImage: ObservableField<String> = ObservableField()
    val description: ObservableField<String> = ObservableField()

    init {
        name.set(item.name)
        coverImage.set(item.coverImage)
        description.set(item.description)
    }

    fun onItemClick() {
        listener.onItemClick(item)
    }

    interface MyCardViewModelListener {
        fun onItemClick(item: MyCard?)
    }
}
