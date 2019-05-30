package ir.fearefull.knowledgeextractionapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceHolder
import android.view.SurfaceView
import io.reactivex.annotations.NonNull
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import ir.fearefull.knowledgeextractionapp.graph.core.bean.ArcUtils
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Dimension
import ir.fearefull.knowledgeextractionapp.graph.core.layout.FRLayout
import ir.fearefull.knowledgeextractionapp.utils.AppConstants
import timber.log.Timber

class GraphSurfaceView : SurfaceView {
    private var mScaleDetector: ScaleGestureDetector? = null

    /**
     * Gets scale factor.
     *
     * @return the scale factor
     */
    var scaleFactor = 1f
        private set

    /**
     * Instantiates a new NetworkGraph surface view.
     *
     * @param context the context
     */
    constructor(context: Context) : super(context) {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
    }


    /**
     * Init.
     *
     * @param graph the graph
     */
    fun init(graph: Graph) {
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                val canvas = holder.lockCanvas(null)
                canvas.drawColor(resources.getColor(R.color.backgroundColorLightTheme))
                drawGraph(canvas, graph)
                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })
    }

    private fun drawGraph(canvas: Canvas, graph: Graph) {
        val paint = Paint()
        val whitePaint = Paint()
        val blackPaint = Paint()
        val accentPaint = Paint()
        paint.isAntiAlias = true
        val layout = FRLayout(graph, Dimension(width, height))
        whitePaint.color = resources.getColor(R.color.backgroundColorLightTheme)
        whitePaint.style = Paint.Style.FILL_AND_STROKE
        whitePaint.strokeWidth = 2f
        whitePaint.textSize = 35f
        whitePaint.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.defaultGraphColorLightTheme))
        blackPaint.color = resources.getColor(R.color.primaryTextColorLightTheme)
        blackPaint.style = Paint.Style.FILL_AND_STROKE
        blackPaint.strokeWidth = 2f
        blackPaint.textSize = 35f
        blackPaint.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.defaultGraphColorLightTheme))
        accentPaint.color = resources.getColor(R.color.secondaryDarkColorLightTheme)
        accentPaint.style = Paint.Style.FILL_AND_STROKE
        accentPaint.strokeWidth = 2f
        accentPaint.setShadowLayer(5f, 0f, 0f, resources.getColor(R.color.defaultGraphColorLightTheme))
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        paint.color = resources.getColor(R.color.graphNodeBackgroundColorLightTheme)
        val curvePaint = Paint()
        curvePaint.isAntiAlias = true
        curvePaint.style = Paint.Style.STROKE
        curvePaint.strokeWidth = 2f
        curvePaint.color = resources.getColor(R.color.secondaryColorLightTheme)
        for (edge in graph.edges) {
            val p1 = layout.transform(edge.from)
            val p2 = layout.transform(edge.to)
            val e1 = PointF(p1!!.x.toFloat(), p1.y.toFloat())
            val e2 = PointF(p2!!.x.toFloat(), p2.y.toFloat())
            Timber.tag("edge").d(edge.getString())
            ArcUtils.drawArc(
                e1,
                e2,
                36f,
                canvas,
                curvePaint,
                blackPaint,
                AppConstants.STROKE_WIDTH_EDGE,
                edge.getString()
            )
        }
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        paint.strokeWidth = 0f
        paint.color = resources.getColor(R.color.backgroundColorLightTheme)
        for (node in graph.nodes) {
            val position = layout.transform(node)

            canvas.drawRoundRect(
                position!!.x.toFloat() - 75,
                position.y.toFloat() - 50,
                position.x.toFloat() + 75,
                position.y.toFloat() + 10,
                10f,
                10f,
                accentPaint
            )
            canvas.drawText(
                node.label, position.x.toFloat(),
                position.y.toFloat(), paint
            )
        }
    }

    /**
     * On touch event.
     *
     * @param ev the ev
     * @return the boolean
     */
    override fun onTouchEvent(@NonNull ev: MotionEvent): Boolean {
        mScaleDetector!!.onTouchEvent(ev)
        return true
    }

    /**
     * The type Scale listener.
     */
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        /**
         * On scale.
         *
         * @param detector the detector
         * @return the boolean
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f))
            invalidate()
            return true
        }
    }

}
