package ir.fearefull.knowledgeextractionapp.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import giwi.org.networkgraph.GraphSurfaceView;
import giwi.org.networkgraph.beans.*;
import giwi.org.networkgraph.layout.FRLayout;
import io.reactivex.annotations.NonNull;
import ir.fearefull.knowledgeextractionapp.R;
import ir.fearefull.knowledgeextractionapp.graph.custom.MyNetworkGraph;
import net.xqhs.graphs.graph.Edge;

public class MyGraphSurfaceView extends GraphSurfaceView {
    private ScaleGestureDetector mScaleDetector;

    private TypedArray attributes;

    private float mScaleFactor = 1.f;

    /**
     * Instantiates a new NetworkGraph surface view.
     *
     * @param context the context
     */
    public MyGraphSurfaceView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MyGraphSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyGraphSurfaceView);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    /**
     * Instantiates a new Graph surface view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public MyGraphSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyGraphSurfaceView);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }


    /**
     * Init.
     *
     * @param graph the graph
     */
    public void init(final MyNetworkGraph graph) {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas(null);
                canvas.drawColor(getResources().getColor(R.color.background));
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

    private void drawGraph(final Canvas canvas, final MyNetworkGraph graph) {
        Paint paint = new Paint();
        Paint whitePaint = new Paint();
        Paint accentPaint = new Paint();
        paint.setAntiAlias(true);
        FRLayout layout = new FRLayout(graph, new Dimension(getWidth(), getHeight()));
        whitePaint.setColor(getResources().getColor(R.color.background_light));
        whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setStrokeWidth(2f);
        whitePaint.setShadowLayer(5, 0, 0, attributes.getColor(R.styleable.GraphSurfaceView_defaultColor, graph
                .getDefaultColor()));
        accentPaint.setColor(getResources().getColor(R.color.colorAccent));
        accentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        accentPaint.setStrokeWidth(2f);
        accentPaint.setShadowLayer(5, 0, 0, attributes.getColor(R.styleable.GraphSurfaceView_defaultColor, graph
                .getDefaultColor()));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20f);
        paint.setColor(getResources().getColor(R.color.background));
        for (Edge edge : graph.getEdges()) {
            Point2D p1 = layout.transform(edge.getFrom());
            Point2D p2 = layout.transform(edge.getTo());
            paint.setStrokeWidth(attributes.getFloat(R.styleable.MyGraphSurfaceView_edgeStrokeWidth, graph.getEdgeStrokeWidth()));
            paint.setColor(getResources().getColor(R.color.colorAccent));
            Paint curve = new Paint();
            curve.setAntiAlias(true);
            curve.setStyle(Paint.Style.STROKE);
            curve.setStrokeWidth(2);
            curve.setColor(getResources().getColor(R.color.background_light));
            PointF e1 = new PointF((float) p1.getX(), (float) p1.getY());
            PointF e2 = new PointF((float) p2.getX(), (float) p2.getY());
            ArcUtils.drawArc(e1, e2, 36f, canvas, curve, paint, whitePaint, graph.getEdgeStrokeWidth());
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50f);
        paint.setStrokeWidth(0f);
        paint.setColor(getResources().getColor(R.color.background_light));
        for (Vertex node : graph.getVertex()) {
            Point2D position = layout.transform(node.getNode());

            canvas.drawRoundRect(
                    (float) position.getX() - 75,
                    (float) position.getY() - 50,
                    (float) position.getX() + 75,
                    (float) position.getY() + 10,
                    10,
                    10,
                    accentPaint);
            canvas.drawText(node.getNode().getLabel(), (float) position.getX(),
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
