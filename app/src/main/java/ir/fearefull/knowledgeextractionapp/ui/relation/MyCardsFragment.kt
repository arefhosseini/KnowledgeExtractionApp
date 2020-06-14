package ir.fearefull.knowledgeextractionapp.ui.relation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard
import ir.fearefull.knowledgeextractionapp.databinding.FragmentMyCardsBinding
import ir.fearefull.knowledgeextractionapp.ui.base.BaseFragment
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import javax.inject.Inject

class MyCardsFragment : BaseFragment<FragmentMyCardsBinding, BaseViewModel<*>>(), MyCardsNavigator, MyCardAdapter.MyCardAdapterListener {

    companion object {
        @JvmStatic val TAG: String = MyCardsFragment::class.java.simpleName

        fun newInstance(): MyCardsFragment {
            val args = Bundle()
            val fragment = MyCardsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var factory: ViewModelProviderFactory
    @Inject
    lateinit var myCardAdapter: MyCardAdapter
    @Inject
    lateinit var layoutManager: LinearLayoutManager

    private lateinit var myCardsViewModel: MyCardsViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_cards

    override fun getViewModel(): BaseViewModel<*> {
        myCardsViewModel = ViewModelProviders.of(this, factory).get(MyCardsViewModel::class.java)
        return myCardsViewModel
    }

    override fun goBack() = getBaseActivity()?.onFragmentDetached(TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCardsViewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myCardAdapter.listener = this
        setUp()
    }

    private fun setUp() {
        layoutManager.orientation = RecyclerView.VERTICAL
        getViewDataBinding().myCardRecyclerView.layoutManager = layoutManager
        getViewDataBinding().myCardRecyclerView.itemAnimator = DefaultItemAnimator()
        getViewDataBinding().myCardRecyclerView.adapter = myCardAdapter
    }

    override fun onMyCardItemClick(item: MyCard?) {
        TODO("Not yet implemented")
    }

}