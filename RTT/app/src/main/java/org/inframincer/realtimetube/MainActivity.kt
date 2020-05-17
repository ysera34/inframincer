package org.inframincer.realtimetube

import android.os.Bundle
import android.support.v4.os.ConfigurationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.inframincer.realtimetube.dummy.DummyContent
import org.inframincer.realtimetube.source.Search
import org.inframincer.realtimetube.source.parser.Parser
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), VideoRecyclerViewFragment.OnListFragmentInteractionListener {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = VideoViewPagerAdapter(supportFragmentManager)
        listOf("title1", "title2", "title3", "title4", "title5", "title6", "title7", "title8", "title9", "title10")
            .forEachIndexed { index, title ->
                adapter.addFragment(VideoRecyclerViewFragment.newInstance(index), title)
            }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
        val searchProvider = Search.getSearchProvider(currentLocale)
        val parser = Parser.getParser(searchProvider)
        val result = parser.parse("response")
        Log.d(TAG, result.joinToString())
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Log.i(TAG, item.toString())
        toast(item.toString())
    }
}
