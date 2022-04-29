package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.NoteItemBinding
import com.example.myapplication.interfaces.TaskClickListener
import com.example.myapplication.model.Task
import com.example.myapplication.sql.DbManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TaskAdapter(private val taskClickListener: TaskClickListener, private var dbManager: DbManager) :
        RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
        private var taskList = ArrayList<Task>()

        class TaskHolder(
            item: View,
            private var taskClickListener: TaskClickListener,
            private var dbManager: DbManager
        ) : RecyclerView.ViewHolder(item) {
            private var binding = NoteItemBinding.bind(item)

            @SuppressLint("SimpleDateFormat")
            var formatter = SimpleDateFormat("dd MMM yyyy HH:mm")

            fun bind(task: Task) {
                binding.apply {
                    noteTitle.text = task.name
                    noteDate.text = formatter.format(task.date)
                    noteContent.text = task.desc
                    checkBox.isChecked = task.done
                    card.isChecked = false

                    card.setOnLongClickListener {
                        card.isChecked = !card.isChecked
                        task.id?.let { x -> taskClickListener.onTaskCheckClick(x) }
                        true
                    }

                    card.setOnClickListener {
                        taskClickListener.onTaskClick(task)
                    }

                    checkBox.setOnClickListener {
                        task.done = checkBox.isChecked
                        task.id?.let { x -> dbManager.updateState(x, task.done) }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
            return TaskHolder(view, taskClickListener, dbManager)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            holder.bind(taskList[position])
        }

        override fun getItemCount(): Int {
            return taskList.size
        }


        fun editedNote(task: Task) {
            taskList = taskList.filter { it.id != task.id } as ArrayList<Task>
            taskList.add(task)
            updateView()
        }

        fun addNote(task: Task) {
            taskList.add(task)
            updateView()
        }

        fun initNotes(tasks: ArrayList<Task>) {
            taskList.clear()
            taskList.addAll(tasks)
            updateView()
        }

        fun deleteByIds(ids: ArrayList<Int>) {
            taskList = taskList.filter { !ids.contains(it.id) } as ArrayList<Task>
            updateView()
        }


        @SuppressLint("NotifyDataSetChanged")
        private fun updateView() {
            notifyDataSetChanged()
        }

}