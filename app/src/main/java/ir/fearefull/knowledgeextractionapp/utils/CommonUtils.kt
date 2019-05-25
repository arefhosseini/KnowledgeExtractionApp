package ir.fearefull.knowledgeextractionapp.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

object CommonUtils {

    fun showLoadingDialog(context: Context): ProgressBar {
        val progressBar = ProgressBar(context)
        progressBar.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        progressBar.visibility = View.VISIBLE
        return progressBar
    }
}