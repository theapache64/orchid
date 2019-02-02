package com.tp.theah64.orchidexample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.theah64.orchidexample.databinding.ItemKeyValueBinding
import com.tp.theah64.orchidexample.models.KeyValue

class KeyValueAdapter(
    val context: Context,
    val keyValues: MutableList<KeyValue>
) : RecyclerView.Adapter<KeyValueAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKeyValueBinding.inflate(layoutInflater, parent, false)
        )
    }

    fun appendKeyValues(keyValues: List<KeyValue>) {
        this.keyValues.addAll(keyValues)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = keyValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.keyValue = keyValues[position]
    }

    fun getShareData(): String {
        //
        val stringBuilder = StringBuilder()

        stringBuilder.append("I love this moviee!! \n\n")

        keyValues.forEach {
            stringBuilder.append("${context.getString(it.key)} : ${it.value} \n\n")
        }
        return stringBuilder.toString()
    }

    // view holder
    class ViewHolder(val binding: ItemKeyValueBinding) : RecyclerView.ViewHolder(binding.root)

}