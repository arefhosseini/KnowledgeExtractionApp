package ir.fearefull.knowledgeextractionapp.ui.relation

interface RelationNavigator {
    fun removeGraph()
    fun showAboutFragment()
    fun handleError(throwable: Throwable)
}