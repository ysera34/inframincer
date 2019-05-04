package org.inframincer.realtimetube


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_video_item.view.*
import org.inframincer.realtimetube.VideoRecyclerViewFragment.OnListFragmentInteractionListener
import org.inframincer.realtimetube.dummy.DummyContent.DummyItem

class VideoRecyclerViewAdapter(
    private val values: List<DummyItem>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.view.item_number.text = item.id
        holder.view.item_content.text = item.content
        holder.view.tag = item
        holder.view.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        override fun toString(): String {
            return super.toString() + " '" + view.item_content.text + "'"
        }
    }
}
