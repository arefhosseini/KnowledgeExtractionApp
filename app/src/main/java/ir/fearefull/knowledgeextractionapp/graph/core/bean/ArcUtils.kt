package ir.fearefull.knowledgeextractionapp.graph.core.bean

import android.graphics.*

object ArcUtils {

    /**
     * https://www.tbray.org/ongoing/When/200x/2009/01/02/Android-Draw-a-Curved-Line
     *
     *
     * Draw arc.
     *
     * @param e1        the e 1
     * @param e2        the e 2
     * @param radius    the radius
     * @param canvas    the canvas
     * @param paint     the paint
     * @param textPaint the text paint
     * @param value     the value
     */
    fun drawArc(
        e1: PointF, e2: PointF, radius: Float, canvas: Canvas, paint: Paint,
        textPaint: Paint, stroke: Long, value: String
    ) {
        val a1 = Math.toRadians((radius + 5).toDouble())
        // l1 is half the length of the line from e1 to e2
        val dx = (e2.x - e1.x).toDouble()
        val dy = (e2.y - e1.y).toDouble()
        val l = Math.sqrt(dx * dx + dy * dy)
        val l1 = l / 2.0
        // h is length of the line from the middle of the connecting line to the center of the circle.
        val h = l1 / Math.tan(a1 / 2.0)
        // r is the radius of the circle
        val r = l1 / Math.sin(a1 / 2.0)
        // a2 is the angle at which L intersects the x axis
        val a2 = Math.atan2(dy, dx)
        // a3 is the angle at which H intersects the x axis
        val a3 = Math.PI / 2.0 - a2
        // m is the midpoint of the line from e1 to e2
        val mX = (e1.x + e2.x) / 2.0
        val mY = (e1.y + e2.y) / 2.0

        // c is the the center of the circle
        val cY = mY + h * Math.sin(a3)
        val cX = mX - h * Math.cos(a3)
        // rect is the square RectF that bounds the "oval"
        val oval = RectF((cX - r).toFloat(), (cY - r).toFloat(), (cX + r).toFloat(), (cY + r).toFloat())

        // a4 is the starting sweep angle
        val rawA4 = Math.atan2(e1.y - cY, e1.x - cX)
        val a4 = Math.toDegrees(rawA4).toFloat()
        paint.strokeWidth = stroke.toFloat()
        drawArrow(e2.x, e2.y, a4 + radius + 45f, paint, canvas)
        canvas.drawArc(oval, a4, radius, false, paint)
        val deltay = -Math.sin(a3) * (r - h)
        val deltax = Math.cos(a3) * (r - h)

        canvas.drawText(
            value, (mX + deltax).toFloat(),
            (mY + deltay).toFloat() + 10, textPaint
        )
    }

    /**
     * Draw arrow.
     *
     * @param x       the x
     * @param y       the y
     * @param degrees the degrees
     * @param paint   the paint
     * @param canvas  the canvas
     */
    private fun drawArrow(x: Float, y: Float, degrees: Float, paint: Paint, canvas: Canvas) {
        canvas.save()
        canvas.rotate(degrees, x, y)
        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(x - 40f, y - 40f)
        path.lineTo(x - 60f, y - 40f)
        path.lineTo(x - 40f, y - 60f)
        path.lineTo(x - 40f, y - 40f)
        path.close()
        canvas.drawPath(path, paint)
        canvas.restore()
        paint.style = Paint.Style.STROKE
    }
}
/**
 * Instantiates a new Arc utils.
 */
