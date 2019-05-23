package ir.fearefull.knowledgeextractionapp.ui.base

import android.content.Context
import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*, *>> : Fragment() {

    var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding!!.root
        return mRootView as View
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}