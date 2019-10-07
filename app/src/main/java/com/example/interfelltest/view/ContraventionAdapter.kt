package com.example.interfelltest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interfelltest.R
import com.example.interfelltest.dblogic.entity.Contravention

class ContraventionAdapter(private val items: List<Contravention>) : RecyclerView.Adapter<ContraventionAdapter.ItemHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_detail, parent, false)

        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.txtDate.text = context.getString(R.string.label_item_date) + item.dateTime
        holder.check.isChecked = item.isContravention
        holder.checkT.isChecked = item.isYearsOrDiscapacy

    }

    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtDate: TextView
        var check: CheckBox
        var checkT: CheckBox

        init {
            txtDate = itemView.findViewById(R.id.txtDate)
            checkT = itemView.findViewById(R.id.checkT)
            check = itemView.findViewById(R.id.check)
        }
    }
}