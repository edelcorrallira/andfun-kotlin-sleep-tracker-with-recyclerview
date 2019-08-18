package com.example.android.trackmysleepquality.sleepquality

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SleepNightAdapter(val clickListener: SleepNightListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()){

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    /**
     * Performs view recycling. Old views that disappear from screen get updated data
     * and are introduced where needed.
     *
     * @param holder Contains [DataItem] data
     *
     * @param position The position of the item in the list, used to determine how the view will be
     * managed
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        when(holder){
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list:List<SleepNight>?){
        //Create lists in background thread
        adapterScope.launch {
            val items = when(list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map {DataItem.SleepNightItem(it)}
            }
            //get back to UI
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }


    /**
     * Creates [View]s with [DataItem] data and hands them over to [ViewHolder]
     * @param parent The [ViewGroup] where the [ViewHolder] is hosted
     *
     * @param viewType Determines the kind of [ViewHolder] to use (in the case where a list contains
     * different types of [ViewHolder]s, in this implementation all are the same)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    /**
     * Adapts [SleepNight] data for use in RecyclerView.
     */
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){

        /**
         * Contains the code that describes how this view should be drawn/updated
         *
         * @param item [SleepNight] item to be displayed
         */
        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        /**
         * Introduced to handle instance creation without exposing constructor details, as a Factory.
         */
        companion object {
            /**
             * Constructs a [ViewHolder] instance.
             * @param parent The [ViewGroup] to which this instance will be attached to.
             * @return a [ViewHolder] with [SleepNight] data and an onClick listener bound.
             */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    /**
     * Adapts the Header for use in RecyclerView.
     */
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view){
        //No bind method due to there being no data or listeners, so inflating is sufficient.
        /**
         * Performs instantiation of the Header singleton.
         */
        companion object {
            /**
             * Constructs the Header Singleton instance.
             * @param parent The [ViewGroup] to which this instance will be attached to, in essence
             * the RecyclerView list.
             * @return The [TextViewHolder] header singleton instance.
             */
            fun from(parent: ViewGroup): TextViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header,parent,false)
                return TextViewHolder(view)
            }
        }
    }

    class SleepNightDiffCallback: DiffUtil.ItemCallback<DataItem>(){
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
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
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
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {

            return oldItem.id == newItem.id
        }

    }

}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit){
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

/**
 * Represents the items used by ViewHolders in lists
 */
sealed class DataItem{
    abstract val id: Long

    /**
     * A wrapper for [SleepNight] items
     */
    data class SleepNightItem(val sleepNight: SleepNight): DataItem(){
        override val id = sleepNight.nightId
    }

    /**
     * The header of the RecyclerView, a singleton since there is only one header in the list
     */
    object Header:DataItem(){
        override val id = Long.MIN_VALUE
    }
}