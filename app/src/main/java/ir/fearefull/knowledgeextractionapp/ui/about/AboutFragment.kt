package ir.fearefull.knowledgeextractionapp.ui.about

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import ir.fearefull.knowledgeextractionapp.ui.base.BaseFragment
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import javax.inject.Inject

class AboutFragment : BaseFragment<ViewDataBinding, BaseViewModel<*>>(), AboutNavigator {

    companion object {
        @JvmStatic val TAG: String = AboutFragment::class.java.simpleName

        fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var aboutViewModel: AboutViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_about

    override fun getViewModel(): BaseViewModel<*> {
        aboutViewModel = ViewModelProviders.of(this, factory).get(AboutViewModel::class.java)
        return aboutViewModel
    }

    override fun goBack() = getBaseActivity()?.onFragmentDetached(TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aboutViewModel.setNavigator(this)
    }

}