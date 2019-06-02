package ir.fearefull.knowledgeextractionapp.data.model.other

data class Graph(
    val nodes: ArrayList<Node> = arrayListOf(),
    val edges: ArrayList<Edge> = arrayListOf()){

    fun addNode(label: String) {
        nodes.add(Node(label))
    }

    fun addEdge(labels: ArrayList<String>, from: Int, to: Int) {
        val fromNode = nodes[from]
        val toNode = nodes[to]
        val edge = Edge(fromNode, toNode)
        edge.addLabels(labels)
        edges.add(edge)
    }

    fun removeNode(node: Node){ if (node in nodes) nodes.remove(node) }

    fun removeEdge(edge: Edge){ if (edge in edges) edges.remove(edge) }

    fun isEmpty(): Boolean {
        if (nodes.isEmpty())
            return true
        return false
    }

    fun clear() {
        nodes.clear()
        edges.clear()
    }
}