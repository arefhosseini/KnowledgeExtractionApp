package ir.fearefull.knowledgeextractionapp.data.model.other

data class Edge(
    val from: Node,
    val to: Node,
    val labels: ArrayList<String> = arrayListOf()) {
    fun addLabel(label: String) = labels.add(label)
    fun removeLabel(label: String) { if (label in labels) labels.remove(label) }
    fun getString(): String {
        var string = "{"
        for (label in labels) {
            string += label
            if (labels.last() != label)
                string += ", "
        }
        string += "}"
        return string
    }
}