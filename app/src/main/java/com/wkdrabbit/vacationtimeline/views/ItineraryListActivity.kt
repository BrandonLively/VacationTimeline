package com.wkdrabbit.vacationtimeline.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wkdrabbit.vacationtimeline.CreateItineraryDialogFragment
import com.wkdrabbit.vacationtimeline.R
import com.wkdrabbit.vacationtimeline.adapters.ItineraryListAdapter
import com.wkdrabbit.vacationtimeline.models.Itinerary
import com.wkdrabbit.vacationtimeline.viewModels.ItineraryViewModel
import kotlinx.android.synthetic.main.itinerary_list.*


class ItineraryListActivity : AppCompatActivity(), CreateItineraryDialogFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var twoPane: Boolean = false
    private lateinit var itineraryViewModel: ItineraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_list)

        setupRecyclerView()
    }

    override fun onPostResume() {

        super.onPostResume()
    }

    private fun setupRecyclerView() {
        rv_itinerary_list.layoutManager = LinearLayoutManager(this)
        rv_itinerary_list.setHasFixedSize(true)

        var adapter = ItineraryListAdapter()

        rv_itinerary_list.adapter = adapter
        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)

        itineraryViewModel.getAllItineraries().observe(this, Observer<List<Itinerary>> {
            updateRecyclerView(adapter, it as MutableList<Itinerary>)
        })

        adapter.setOnItemClickListener(object : ItineraryListAdapter.OnItemClickListener {
            override fun onItemClick(itinerary: Itinerary) {

                if (itinerary.destinationName.equals("Create New Trip")) {
                    val fm = supportFragmentManager
                    var dialogFragment = CreateItineraryDialogFragment()
                    dialogFragment.show(fm, "Testing")

                } else {
                    var intent = Intent(baseContext, AttractionListActivity::class.java)
                    intent.putExtra("id", itinerary.itinerary_id)
                    intent.putExtra("title", itinerary.destinationName)
                    startActivity(intent)
                }

            }
        })
    }

    fun updateRecyclerView(adapter: ItineraryListAdapter, itineraryList: MutableList<Itinerary>) {
        itineraryList.add(0, Itinerary(11, "Create New Trip"))
        adapter.submitList(itineraryList as List<Itinerary>)
    }


}
