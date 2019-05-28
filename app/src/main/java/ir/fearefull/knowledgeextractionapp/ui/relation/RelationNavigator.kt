package ir.fearefull.knowledgeextractionapp.ui.relation

interface RelationNavigator {

    fun createGraph()
    fun removeGraph()
    fun handleError(throwable: Throwable)
}