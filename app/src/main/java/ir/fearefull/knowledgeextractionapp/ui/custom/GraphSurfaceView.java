package ir.fearefull.knowledgeextractionapp.ui.custom;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import io.reactivex.annotations.NonNull;
import ir.fearefull.knowledgeextractionapp.R;
import ir.fearefull.knowledgeextractionapp.data.model.other.Edge;
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph;
import ir.fearefull.knowledgeextractionapp.data.model.other.Node;
import ir.fearefull.knowledgeextractionapp.graph.core.bean.ArcUtils;
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Dimension;
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Point2D;
import ir.fearefull.knowledgeextractionapp.graph.core.layout.FRLayout;
import ir.fearefull.knowledgeextractionapp.utils.AppConstants;
import timber.log.Timber;

public class GraphSurfaceView extends SurfaceView {
    private ScaleGestureDetector mScaleDetector;

    private float mScaleFactor = 1.f;

    /**
     * Instantiates a new NetworkGraph surface view.
     *
     * @param context the context
     */
    public GraphSurfaceView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public GraphSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public GraphSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }


    /**
     * Init.
     *
     * @param graph the graph
     */
    public void init(final Graph graph) {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas(null);
                canvas.drawColor(getResources().getColor(R.color.backgroundColorLightTheme));
                drawGraph(canvas, graph);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    private void drawGraph(final Canvas canvas, final Graph graph) {
        Paint paint = new Paint();
        Paint whitePaint = new Paint();
        Paint blackPaint = new Paint();
        Paint accentPaint = new Paint();
        paint.setAntiAlias(true);
        FRLayout layout = new FRLayout(graph, new Dimension(getWidth(), getHeight()));
        whitePaint.setColor(getResources().getColor(R.color.backgroundColorLightTheme));
        whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setStrokeWidth(2f);
        whitePaint.setTextSize(35);
        whitePaint.setShadowLayer(5, 0, 0, getResources().getColor(R.color.defaultGraphColorLightTheme));
        blackPaint.setColor(getResources().getColor(R.color.primaryTextColorLightTheme));
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blackPaint.setStrokeWidth(2f);
        blackPaint.setTextSize(35);
        blackPaint.setShadowLayer(5, 0, 0, getResources().getColor(R.color.defaultGraphColorLightTheme));
        accentPaint.setColor(getResources().getColor(R.color.secondaryDarkColorLightTheme));
        accentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        accentPaint.setStrokeWidth(2f);
        accentPaint.setShadowLayer(5, 0, 0, getResources().getColor(R.color.defaultGraphColorLightTheme));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50f);
        paint.setColor(getResources().getColor(R.color.graphNodeBackgroundColorLightTheme));
        Paint curvePaint = new Paint();
        curvePaint.setAntiAlias(true);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setStrokeWidth(2);
        curvePaint.setColor(getResources().getColor(R.color.secondaryColorLightTheme));
        for (Edge edge : graph.getEdges()) {
            Point2D p1 = layout.transform(edge.getFrom());
            Point2D p2 = layout.transform(edge.getTo());
            PointF e1 = new PointF((float) p1.getX(), (float) p1.getY());
            PointF e2 = new PointF((float) p2.getX(), (float) p2.getY());
            Timber.tag("edge").d(edge.getString());
            ArcUtils.drawArc(e1, e2, 36f, canvas, curvePaint, blackPaint, AppConstants.STROKE_WIDTH_EDGE, edge.getString());
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50f);
        paint.setStrokeWidth(0f);
        paint.setColor(getResources().getColor(R.color.backgroundColorLightTheme));
        for (Node node : graph.getNodes()) {
            Point2D position = layout.transform(node);

            canvas.drawRoundRect(
                    (float) position.getX() - 75,
                    (float) position.getY() - 50,
                    (float) position.getX() + 75,
                    (float) position.getY() + 10,
                    10,
                    10,
                    accentPaint);
            canvas.drawText(node.getLabel(), (float) position.getX(),
                    (float) position.getY(), paint);
        }
    }

    /**
     * On touch event.
     *
     * @param ev the ev
     * @return the boolean
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    /**
     * Gets scale factor.
     *
     * @return the scale factor
     */
    public float getScaleFactor() {
        return mScaleFactor;
    }

    /**
     * The type Scale listener.
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        /**
         * On scale.
         *
         * @param detector the detector
         * @return the boolean
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }

}
