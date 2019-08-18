package com.example.android.trackmysleepquality.sleepquality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()){

    /**
     * Performs view recycling. Old views that disappear from screen get updated data
     * and are introduced where needed.
     *
     * @param holder Contains [SleepNight] data
     *
     * @param position The position of the item in the list, used to determine how the view will be
     * managed
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position))
    }



    /**
     * Creates [View]s with [SleepNight] data and hands them over to [ViewHolder]
     * @param parent The [ViewGroup] where the [ViewHolder] is hosted
     *
     * @param viewType Determines the kind of [ViewHolder] to use (in the case where a list contains
     * different types of [ViewHolder]s, in this implementation all are the same)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Adapts [SleepNight] data to list_item_sleep_night layout.
     */
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){

        /**
         * Contains the code that describes how this view should be drawn/updated
         *
         * @param item [SleepNight] item to be displayed
         */
        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()
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
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>(){
        /**
         * Called to check whether two items have the same data.
         *
         * This information is used to detect if the contents of an item have changed.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the contents of the items are the same or false if they are different.
         *
         */
        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

        /**
         * Called to check whether two objects represent the same item.
         *
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return True if the two items represent the same object or false if they are different.
         *
         */
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

    }

}