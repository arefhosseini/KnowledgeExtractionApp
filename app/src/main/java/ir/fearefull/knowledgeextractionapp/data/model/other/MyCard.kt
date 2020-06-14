package ir.fearefull.knowledgeextractionapp.data.model.other

import ir.fearefull.knowledgeextractionapp.databinding.MyCardBinding

data class MyCard(
    val name: String,
    val description: String,
    val coverImage: String,
    var binding: MyCardBinding?
)