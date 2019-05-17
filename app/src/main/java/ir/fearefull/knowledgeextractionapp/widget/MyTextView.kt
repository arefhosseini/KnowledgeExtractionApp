package ir.fearefull.knowledgeextractionapp.widget

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

class MyTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    init {
        val typeface = Typeface.createFromAsset(getContext().assets, "font/my_font.ttf")
        setTypeface(typeface)
    }
}