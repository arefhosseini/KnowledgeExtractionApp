package ir.fearefull.knowledgeextractionapp.utils

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard
import ir.fearefull.knowledgeextractionapp.ui.relation.MyCardAdapter
import ir.fearefull.knowledgeextractionapp.utils.extension.getParentActivity

object BindingUtils {
    @BindingAdapter("mutableVisibility")
    @JvmStatic
    fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if (parentActivity != null && visibility != null) {
            visibility.observe(parentActivity, Observer { value -> view.visibility = value?: View.VISIBLE})
        }
    }

    @BindingAdapter("mutableText")
    @JvmStatic
    fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if (parentActivity != null && text != null) {
            text.observe(parentActivity, Observer { value -> view.text = value?:""})
        }
    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun setAdapter(recyclerView: RecyclerView, myCards: List<MyCard>) {
        val adapter: MyCardAdapter? = recyclerView.adapter as MyCardAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(myCards)
        }
    }

    /*@BindingAdapter("networkGraph")
    @JvmStatic
    fun setNetworkGraph(view: GraphSurfaceView, networkGraph: NetworkGraph) {
        view.init(networkGraph)
    }*/

    @BindingAdapter("onEditorSearchAction")
    @JvmStatic
    fun EditText.onEditorSearchAction(f: Function1<String, Unit>?) {

        if (f == null) setOnEditorActionListener(null)
        else setOnEditorActionListener { v, actionId, event ->

            val imeAction = when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> true
                else -> false
            }

            val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
                    && event.action == KeyEvent.ACTION_DOWN

            if (imeAction or keydownEvent)
                true.also { f(v.editableText.toString()) }
            else false
        }
    }
}