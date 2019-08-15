package com.example.android.trackmysleepquality.sleepquality

import android.content.res.Resources
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

    /**
     * Returns the number of items in [RecyclerView]
     * @return size The amount of [ViewHolder]s in [RecyclerView]
     */
    override fun getItemCount() = data.size //Amount in recycler view

    /**
     * Performs view recycling. Old views that disappear from screen get updated data
     * and are introduced where needed.
     *
     * @param holder Contains SleepNight data, although currently it's a stub containing only the
     * sleep quality
     *
     * @param position The position of the item in the list, used to determine how the view will be
     * managed
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = data[position] //Position view is in
        holder.bind(item)
    }



    /**
     * Creates [View]s with [SleepNight] data and hands them over to [ViewHolder]
     * @param parent The [ViewGroup] where the [ViewHolder] is hosted
     *
     * @param viewType Determines the kind of [ViewHolder] to use (in the case where a list contains
     * different types of ViewHolders, in this implementation all are the same)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Adapts [SleepNight] data to list_item_sleep_night layout.
     */
    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

        /**
         * Contains the code that describes how this view should be drawn/updated
         *
         * @param item [SleepNight] item to be displayed
         */
        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(when (item.sleepQuality) {
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
         * Introduced to handle instance creation without exposing constructor details, as a Factory.
         */
        companion object {
            /**
             * Constructs a ViewHolder instance.
             * @param parent The [ViewGroup] to which this instance will be attached to.
             */
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
            }
        }
    }


}