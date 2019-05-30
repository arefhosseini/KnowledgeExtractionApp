package ir.fearefull.knowledgeextractionapp.graph.core.bean

data class Point2D (
    var x: Double = 0.0,
    var y: Double = 0.0) {
    /**
     * Sets location.
     *
     * @param x the x
     * @param y the y
     */
    fun setLocation(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    /**
     * Sets location.
     *
     * @param p the p
     */
    fun setLocation(p: Point2D) {
        this.x = p.x
        this.y = p.y
    }

}