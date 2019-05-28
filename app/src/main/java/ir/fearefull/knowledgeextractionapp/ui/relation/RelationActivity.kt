package ir.fearefull.knowledgeextractionapp.ui.relation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import giwi.org.networkgraph.beans.Vertex
import ir.fearefull.knowledgeextractionapp.BR
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ViewModelProviderFactory
import ir.fearefull.knowledgeextractionapp.databinding.ActivityRelationBinding
import ir.fearefull.knowledgeextractionapp.graph.custom.MyNetworkGraph
import ir.fearefull.knowledgeextractionapp.ui.about.AboutFragment
import ir.fearefull.knowledgeextractionapp.ui.base.BaseActivity
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.ui.custom.MyGraphSurfaceView
import net.xqhs.graphs.graph.Node
import net.xqhs.graphs.graph.SimpleEdge
import net.xqhs.graphs.graph.SimpleNode
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
    private lateinit var graphLayout: RelativeLayout

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
        graphLayout = relationActivityBinding.graphLayout
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
                graphLayout, false) as MyGraphSurfaceView
        graphLayout.addView(graphSurface)

        val graph = MyNetworkGraph(applicationContext)

        graph.defaultColor = ContextCompat.getColor(this, android.R.color.black)
        graph.edgeColor = ContextCompat.getColor(this, android.R.color.holo_blue_light)
        graph.nodeColor = ContextCompat.getColor(this, android.R.color.holo_blue_light)
        graph.nodeBgColor = ContextCompat.getColor(this, android.R.color.white)

        var v1: Node = SimpleNode("18")
        val v2: Node = SimpleNode("24")
        graph.vertex.add(Vertex(v1, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.vertex.add(Vertex(v2, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.addEdge(SimpleEdge(v1, v2, "12"))

        val v3: Node = SimpleNode("7")
        graph.vertex.add(Vertex(v3, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.addEdge(SimpleEdge(v2, v3, "23"))

        v1 = SimpleNode("14")
        graph.vertex.add(Vertex(v1, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.addEdge(SimpleEdge(v3, v1, "34"))

        v1 = SimpleNode("10")
        graph.vertex.add(Vertex(v1, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.addEdge(SimpleEdge(v3, v1, "35"))

        v1 = SimpleNode("11")
        graph.vertex.add(Vertex(v1, ContextCompat.getDrawable(this, R.mipmap.avatar)))
        graph.addEdge(SimpleEdge(v1, v3, "36"))
        graph.addEdge(SimpleEdge(v3, v1, "6"))

        graphSurface.init(graph)
        //graphSurface.createGraph()
    }

    override fun removeGraph() {
        graphLayout.removeAllViews()
    }
}