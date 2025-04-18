package com.example.todo_native.ui.main

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_native.R



class MainActivity : AppCompatActivity() {

    private lateinit var todoList: MutableList<String>
    private lateinit var adapter: TodoAdapter
    private lateinit var etTask: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTask = findViewById(R.id.editTask)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.todoRecycler)

        todoList = mutableListOf("Prepare a coffee", "Buy Groceries", "Study Kotlin")

        adapter = TodoAdapter(
            todoList,
            onDeleteClicked = { position ->
                todoList.removeAt(position)
                adapter.notifyItemRemoved(position)
            },
            onItemClicked = { position ->
                showEditDialog(position)
            }
        )


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val task = etTask.text.toString().trim()
            if (task.isNotEmpty()) {
                todoList.add(task)
                adapter.notifyItemInserted(todoList.size - 1)
                etTask.text.clear()
                recyclerView.scrollToPosition(todoList.size - 1)
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDialog(position: Int) {
        val editText = EditText(this).apply {
            setText(todoList[position])
            setSelection(text.length)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Update") { _, _ ->
                val updatedTask = editText.text.toString().trim()
                if (updatedTask.isNotEmpty()) {
                    todoList[position] = updatedTask
                    adapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}




