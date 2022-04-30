package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activities.EditActivity
import com.example.myapplication.adapter.TaskAdapter
import com.example.myapplication.databinding.FragmentNotesListBinding
import com.example.myapplication.enums.OperationType
import com.example.myapplication.interfaces.TaskClickListener
import com.example.myapplication.model.Task
import com.example.myapplication.sql.DbManager
import com.example.myapplication.adapter.DateTimeAdapter

class TasksListFragment : Fragment(), TaskClickListener {
    private lateinit var binding: FragmentNotesListBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var adapter: TaskAdapter
    private lateinit var dbManager: DbManager
    private var toDeleteList = ArrayList<Int>()

    companion object {
        @JvmStatic
        fun newInstance() = TasksListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DbManager(requireContext())
        binding = FragmentNotesListBinding.inflate(inflater)
        initLauncherAndAdapter()
        initRecyclerView()
        initListeners()
        return binding.root
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(requireContext(), EditActivity::class.java).apply {
            putExtra("task", task)
        }
        launcher.launch(intent)
    }

    override fun onTaskCheckClick(id: Int) {
        if (toDeleteList.find { x -> x == id } != null) {
            toDeleteList.remove(id)
        } else {
            toDeleteList.add(id)
        }
        updateAddButtonView()
    }

    private fun updateAddButtonView() {
        binding.addNote.icon = ContextCompat.getDrawable(
            requireContext(),
            if (toDeleteList.isEmpty()) R.drawable.ic_add else R.drawable.ic_delete_24
        )
    }

    private fun initLauncherAndAdapter() {
        adapter = TaskAdapter(this, dbManager)
        dbManager.OpenDb()
        adapter.initNotes(dbManager.readAll)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val type = OperationType.valueOf(it.data?.getSerializableExtra("type").toString())
                val task = it.data?.getSerializableExtra("task") as Task
                if (type == OperationType.ADD) {
                    task.id = dbManager.insert(
                        task.name,
                        DateTimeAdapter.toString(task.date, DateTimeAdapter.DATE_TIME),
                        task.desc,
                        task.done.toString()
                    ).toInt()
                    adapter.addNote(task)
                } else if (type == OperationType.EDIT) {
                    dbManager.update(task)
                    adapter.editedNote(task)
                }
            }
        }

    }

    private fun initListeners() {
        binding.apply {
            addNote.setOnClickListener {
                if (toDeleteList.isEmpty()) {
                    val intent = Intent(requireContext(), EditActivity::class.java).apply{}
                    launcher.launch(intent)
                } else {
                    dbManager.deleteAllById(toDeleteList)
                    adapter.deleteByIds(toDeleteList)
                    toDeleteList.clear()
                    updateAddButtonView()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            noteRcView.layoutManager =
                if (resources.configuration.orientation == 2) GridLayoutManager(
                    requireContext(),
                    2
                ) else LinearLayoutManager(requireContext())
            noteRcView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

}