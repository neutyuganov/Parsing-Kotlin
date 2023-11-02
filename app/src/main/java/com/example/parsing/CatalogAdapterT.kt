package com.example.parsing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CatalogAdapterT(private val listRecyclerItem: ArrayList<CatalogDataClass>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Присваиваем поля для заполнения элемента RecyclerView
        var id: TextView = itemView.findViewById<TextView>(R.id.id)
        var name: TextView = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
// Создаем представление из Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
// Заполняем элемент данными
        val _holder = holder as ItemViewHolder
        val catalog: CatalogDataClass = listRecyclerItem[position]
        _holder.id.setText(catalog.id)
        _holder.name.setText(catalog.name)
        // Пример реализации setOnClickListener для этого представления
    }

    override fun getItemCount(): Int {
// Получает всёё количесво элементов RecyclerView
        return listRecyclerItem.size
    }
}