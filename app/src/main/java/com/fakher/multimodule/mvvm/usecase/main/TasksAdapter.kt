package com.fakher.multimodule.mvvm.usecase.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fakher.multimodule.mvvm.R
import com.fakher.multimodule.mvvm.database.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskItemViewHolder>() {

    private val tasks = arrayListOf<Task>()

    fun onAddTasks(newTasks: List<Task>) {
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
    )


    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    class TaskItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.item_task_title
        private val completed = view.item_task_completed

        fun bind(taskItem: Task) {
            title.text = taskItem.title
            completed.isChecked = taskItem.completed
        }
    }
}