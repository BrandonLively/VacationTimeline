package com.wkdrabbit.vacationtimeline.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wkdrabbit.vacationtimeline.R
import com.wkdrabbit.vacationtimeline.data.Constants
import com.wkdrabbit.vacationtimeline.models.Itinerary
import kotlinx.android.synthetic.main.itinerary_list_content.view.*
import kotlinx.android.synthetic.main.itinerary_list_content.view.iv_itinerary_background
import kotlinx.android.synthetic.main.itinerary_list_content.view.tv_destination_name
import java.util.*

class ItineraryListAdapter :
    androidx.recyclerview.widget.ListAdapter<Itinerary, ItineraryListAdapter.ItineraryHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Itinerary>() {
            override fun areItemsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id
            }

            override fun areContentsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id &&
                        oldItem.timeVisited == newItem.timeVisited &&
                        oldItem.destinationName == newItem.destinationName
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.itinerary_list_content, parent, false)
        return ItineraryHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItineraryHolder, position: Int) {
        val currentItinerary: Itinerary = getItem(position)

        holder.tvYearVisted.text = Constants.itineraryCardFormat.format(Date(currentItinerary.timeVisited * 1000L))
        holder.tvDestination.text = currentItinerary.destinationName

        //Sets up the Create New Trip Card Styling
        if (position == 0) {
            holder.tvYearVisted.visibility = View.GONE
            holder.tvDestination.visibility = View.GONE
            holder.cardContainer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 125)
            holder.cardContainer.setCardBackgroundColor(holder.tvDestination.context.resources.getColor(R.color.material_grey_500))
            holder.ivBackgroundTint.visibility = View.GONE
            holder.ivBackgroundImage.scaleType = ImageView.ScaleType.FIT_CENTER
            holder.ivBackgroundImage.setImageResource(R.drawable.ic_create_trip)
        } else {
            holder.tvYearVisted.visibility = View.VISIBLE
            holder.tvDestination.visibility = View.VISIBLE
            holder.ivBackgroundTint.visibility = View.VISIBLE
            holder.ivBackgroundImage.scaleType = ImageView.ScaleType.CENTER_CROP
            holder.cardContainer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250)
        }

        var imageResource: Int = R.drawable.stockitinimages1

        if(position != 0){
        when (position % 10) {
            0 -> imageResource = R.drawable.stockitinimages1
            1 -> imageResource = R.drawable.stockitinimages2
            2 -> imageResource = R.drawable.stockitinimages3
            3 -> imageResource = R.drawable.stockitinimages4
            4 -> imageResource = R.drawable.stockitinimages5
            5 -> imageResource = R.drawable.stockitinimages6
            6 -> imageResource = R.drawable.stockitinimages7
            7 -> imageResource = R.drawable.stockitinimages8
            8 -> imageResource = R.drawable.stockitinimages9
            9 -> imageResource = R.drawable.stockitinimages10
        }
        holder.ivBackgroundImage.setImageResource(imageResource)
    }}

    fun getItineraryAt(position: Int): Itinerary {
        return getItem(position)
    }

    inner class ItineraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tvDestination: TextView = itemView.tv_destination_name
        var tvYearVisted: TextView = itemView.tv_year_visited
        var ivBackgroundImage: ImageView = itemView.iv_itinerary_background
        var cardContainer = itemView.card_container
        var ivBackgroundTint = itemView.iv_backgroud_tint
    }

    interface OnItemClickListener {
        fun onItemClick(itinerary: Itinerary)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}