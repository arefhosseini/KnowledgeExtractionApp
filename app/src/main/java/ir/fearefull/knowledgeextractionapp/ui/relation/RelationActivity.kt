package ir.fearefull.knowledgeextractionapp.ui.relation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
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
import kotlinx.android.synthetic.main.activity_relation.*
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
    private lateinit var animation: Animation
    private var errorSnackbar: Snackbar? = null

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
        if (graphLayout.childCount > 0)
            relationActivityBinding.graphLayout.findViewById<GraphSurfaceView>(R.id.graphSurface).visibility = View.VISIBLE
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relationActivityBinding = getViewDataBinding() as ActivityRelationBinding
        relationViewModel.setNavigator(this)
        relationViewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_right)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        setUp()
    }

    private fun setUp() {
        relationActivityBinding.toolbar

        setSupportActionBar(relationActivityBinding.toolbar)
    }

    override fun showAboutFragment() {
        hideKeyboard()
        if (graphLayout.childCount > 0)
            relationActivityBinding.graphLayout.findViewById<GraphSurfaceView>(R.id.graphSurface).visibility = View.GONE
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.rootView, AboutFragment.newInstance(), AboutFragment.TAG)
            .commit()
    }


    private fun showError(@StringRes errorMessage:Int){
        hideKeyboard()
        errorSnackbar = Snackbar.make(relationActivityBinding.rootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, relationViewModel.errorClickListener)
        errorSnackbar?.setActionTextColor(ContextCompat.getColor(baseContext, R.color.secondaryTextColorLightTheme))
        ViewCompat.setLayoutDirection(errorSnackbar!!.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        errorSnackbar!!.view.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.secondaryColorLightTheme))
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun createGraph() {
        hideKeyboard()
        val graphSurface =
            LayoutInflater.from(applicationContext).inflate(R.layout.item_graph_surface_view,
                relationActivityBinding.graphLayout, false) as GraphSurfaceView
        relationActivityBinding.graphLayout.addView(graphSurface)

        graphSurface.init(relationViewModel.getGraph())
        //graphSurface.createGraph()
    }

    override fun removeGraph() {
        relationActivityBinding.graphLayout.removeAllViews()
    }
}