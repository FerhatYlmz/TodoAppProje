package com.example.todo_native.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_native.R


class TodoAdapter(
    private val tasks: MutableList<String>,
    private val onDeleteClicked: (Int) -> Unit,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoText: TextView = itemView.findViewById(R.id.todoText)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.todoText.text = tasks[position]

        holder.deleteBtn.setOnClickListener {
            Log.d("TodoAdapter", "Deleting item at index: $position")
            if (position in tasks.indices) {
                onDeleteClicked(position)
            } else {
                Log.e("TodoAdapter ", "Invalid position: $position")
            }
        }

        holder.todoText.setOnClickListener {
            Log.d("TodoAdapter", "Clicked on item at index: $position")
            onItemClicked(position)
        }
    }

    override fun getItemCount(): Int = tasks.size
}
