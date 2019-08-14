package com.example.android.trackmysleepquality.sleepquality

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>(){

    var data = listOf<SleepNight>()

    // Updates view holder data, currently it does so globally. Later, a more efficient mehtod will
    //be employed
    set(value){
        field = value //Sets the data the recycler view cares about
        notifyDataSetChanged() //Tells the recycler view of updates
    }

    override fun getItemCount() = data.size //Amount in recycler view

    /**
     * This method is where view recycling happens. Old views that disappear from screen get updated
     * data and are introduced where needed. In short it describes how drawing is performed.
     *
     * @param holder Contains SleepNight data, although currently it's a stub containing only the
     * sleep quality
     *
     * @param position The position of the item in the list, used to determine how the view will be
     * managed
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = data[position] //Position view is in
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli,item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
        holder.qualityImage.setImageResource(when (item.sleepQuality){
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }

    /**
     * Creates Views of SleepNight data and hands them over to ViewHolder
     * @param parent The ViewGroup where the ViewHolder is hosted
     *
     * @param viewType Determines the kind of ViewHolder to use (in the case where a list contains
     * different types of ViewHolders, in this implementation all are the same)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
        return ViewHolder(view)
    }

    /**
     * Adapts SleepNight data to list_item_sleep_night layout
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
    }
}