package ir.fearefull.knowledgeextractionapp.ui.relation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import ir.fearefull.knowledgeextractionapp.databinding.ActivityRelationBinding
import ir.fearefull.knowledgeextractionapp.ui.about.AboutFragment
import ir.fearefull.knowledgeextractionapp.ui.base.BaseActivity
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.ui.custom.GraphSurfaceView
import timber.log.Timber
import javax.inject.Inject

class RelationActivity: BaseActivity<ViewDataBinding, BaseViewModel<*>>(), RelationNavigator,
    HasSupportFragmentInjector {


    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var relationViewModel: RelationViewModel
    private lateinit var relationActivityBinding: ActivityRelationBinding

    companion object {
        fun newIntent(context: Context) = Intent(context, RelationActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_relation

    override fun getViewModel(): BaseViewModel<*> {
        relationViewModel = ViewModelProviders.of(this, factory).get(RelationViewModel::class.java)
        return relationViewModel
    }

    override fun handleError(throwable: Throwable) {
        Timber.e(throwable.localizedMessage)
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached(AboutFragment.TAG)
        }
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(fragment)
                .commitNow()
        }
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relationActivityBinding = getViewDataBinding() as ActivityRelationBinding
        relationViewModel.setNavigator(this)
        setUp()
    }

    private fun setUp() {
        relationActivityBinding.graphLayout

    }

    private fun showAboutFragment() {
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.rootView, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
    }

    override fun createGraph() {
        hideKeyboard()

        val graphSurface =
            LayoutInflater.from(applicationContext).inflate(R.layout.graph_surface_view,
                relationActivityBinding.graphLayout, false) as GraphSurfaceView
        relationActivityBinding.graphLayout.addView(graphSurface)

        graphSurface.init(relationViewModel.getGraph())
        //graphSurface.createGraph()
    }

    override fun removeGraph() {
        relationActivityBinding.graphLayout.removeAllViews()
    }
}