package ir.fearefull.knowledgeextractionapp.ui.relation

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import ir.fearefull.knowledgeextractionapp.ui.base.BaseActivity
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import android.content.Intent
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import androidx.lifecycle.ViewModelProviders
import ir.fearefull.knowledgeextractionapp.ui.about.AboutFragment
import ir.fearefull.knowledgeextractionapp.databinding.ActivityRelationBinding
import android.os.Bundle
import android.util.Log

class RelationActivity: BaseActivity<ViewDataBinding, BaseViewModel<*>>(), RelationNavigator,
    HasSupportFragmentInjector {


    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var relationViewModel: RelationViewModel
    private lateinit var relationMainBinding: ActivityRelationBinding


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
        Log.d("Error", throwable.localizedMessage)
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
        relationMainBinding = getViewDataBinding() as ActivityRelationBinding
        relationViewModel.setNavigator(this)
        setUp()
    }

    private fun setUp() {

    }

    private fun showAboutFragment() {
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
    }

}