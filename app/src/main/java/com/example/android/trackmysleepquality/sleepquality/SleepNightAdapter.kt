package com.example.android.trackmysleepquality.sleepquality

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>(){

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
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int){
        val item = data[position] //Position view is in
        holder.textView.text = item.sleepQuality.toString()
    }

    /**
     * Creates Views of SleepNight data and hands them over to ViewHolder
     * @param parent The ViewGroup where the ViewHolder is hosted
     *
     * @param viewType Determines the kind of ViewHolder to use (in the case where a list contains
     * different types of ViewHolders, in this implementation all are the same)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

}