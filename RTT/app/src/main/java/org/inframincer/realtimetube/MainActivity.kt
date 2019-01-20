package org.inframincer.realtimetube

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import org.inframincer.realtimetube.dummy.DummyContent

class MainActivity : AppCompatActivity(), VideoRecyclerViewFragment.OnListFragmentInteractionListener {

    lateinit var toolbar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = VideoViewPagerAdapter(supportFragmentManager)
        listOf("title1", "title2", "title3", "title4", "title5", "title1", "title2", "title3", "title4", "title5")
            .forEachIndexed { index, string ->
            adapter.addFragment(VideoRecyclerViewFragment.newInstance(index), string)
        }

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Log.i("MainActivity", item.toString())
    }
}
