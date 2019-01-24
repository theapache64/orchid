package com.tp.orchid.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.orchid.databinding.ItemKeyValueBinding
import com.tp.orchid.models.KeyValue

class KeyValueAdapter(
    context: Context,
    val keyValues: MutableList<KeyValue>
) : RecyclerView.Adapter<KeyValueAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKeyValueBinding.inflate(layoutInflater, parent, false)
        )
    }

    fun appendKeyValues(keyValues: List<KeyValue>) {
        val beforeAppend = this.keyValues.size
        this.keyValues.addAll(keyValues)
        this.notifyItemRangeInserted(beforeAppend, keyValues.size)
    }

    override fun getItemCount(): Int = keyValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binbing.keyValue = keyValues[position]
    }

    // view holder
    class ViewHolder(val binbing: ItemKeyValueBinding) : RecyclerView.ViewHolder(binbing.root)

}