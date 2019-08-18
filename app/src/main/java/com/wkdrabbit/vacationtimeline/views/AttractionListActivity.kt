package com.wkdrabbit.vacationtimeline.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wkdrabbit.vacationtimeline.R
import com.wkdrabbit.vacationtimeline.adapters.AttractionListAdapter
import com.wkdrabbit.vacationtimeline.models.*
import com.wkdrabbit.vacationtimeline.viewModels.AttractionViewModel
import kotlinx.android.synthetic.main.activity_attraction_list.*

// Basil 7/24/2019

class AttractionListActivity : AppCompatActivity() {

    private lateinit var attractionViewModel: AttractionViewModel

    var itineraryId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)

        var bundle: Bundle? = intent.extras
        itineraryId = bundle!!.getInt("id", 0)

        var title : TextView = tv_attraction_title
        title.text = bundle.getString("title","Title")

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_attraction_list.layoutManager = LinearLayoutManager(this)
        rv_attraction_list.setHasFixedSize(true)

        var adapter = AttractionListAdapter()

        rv_attraction_list.adapter = adapter
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)

        attractionViewModel.getAttractionById(itineraryId).observe(this, Observer<List<Attraction>> {
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(object : AttractionListAdapter.OnItemClickListener {
            override fun onItemClick(attraction: Attraction) {
            }
        })
    }
}