package ir.fearefull.knowledgeextractionapp.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import ir.fearefull.knowledgeextractionapp.ui.base.BaseActivity
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationActivity
import ir.fearefull.knowledgeextractionapp.utils.AppLogger
import javax.inject.Inject

class SplashActivity : BaseActivity<ViewDataBinding, BaseViewModel<*>>(), SplashNavigator {
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var splashViewModel: SplashViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun getViewModel(): BaseViewModel<*> {
        splashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        AppLogger.d("created")
        return splashViewModel
    }

    override fun scheduleSplashScreen() {
        val splashScreenDuration = splashViewModel.getSplashScreenDuration()
        Handler().postDelayed(
            {
                // After the splash screen duration, route to the right activities
                splashViewModel.openRelationActivity()
                finish()
            },
            splashScreenDuration
        )
    }

    override fun openRelationActivity() {
        val intent = RelationActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.setNavigator(this)
        splashViewModel.scheduleSplashScreen()
    }
}
