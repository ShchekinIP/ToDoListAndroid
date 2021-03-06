package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.enums.OperationType
import com.example.myapplication.model.Task
import com.example.myapplication.adapter.DateTimeAdapter
import com.example.myapplication.utils.LocaleUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private lateinit var task : Task
    private lateinit var type : OperationType
    private var dataText: String = ""
    private var timeText: String = ""


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        initData()
        initPickers()
        initListeners()
        setContentView(binding.root)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleUtils.attachBaseContext(it) })
    }

    private fun initListeners() {
        val type = type
        binding.save.setOnClickListener {
            if (canSave()) {
                val task = Task(
                    task.id,
                    binding.titleInput.text.toString(),
                    DateTimeAdapter.concatDateAndTime(dataText, timeText),
                    binding.contentInput.text.toString(), false
                )
                val addTask = Intent().apply {
                    putExtra("task", task)
                    putExtra("type", type.toString())
                }
                setResult(RESULT_OK, addTask)
                finish()
            }
        }

        binding.apply {
            titleInput.addTextChangedListener {
                if (binding.titleInput.text.toString().isNotEmpty()) {
                    binding.titleInput.error = null
                }
            }
        }
    }

    private fun canSave(): Boolean {
        binding.apply {
            if (titleInput.text.toString().isEmpty()) {
                titleInput.error = resources.getString(R.string.required)
            }
        }
        return binding.titleInput.error == null
    }

    private fun initData() {
        if (intent.hasExtra("task")) {
            task = intent.getSerializableExtra("task") as Task
            type = OperationType.EDIT
        } else {
            task = Task()
            type = OperationType.ADD
        }
        dataText = DateTimeAdapter.toString(task.date, DateTimeAdapter.DATE)
        timeText = DateTimeAdapter.toString(task.date, DateTimeAdapter.TIME)
        binding.apply {
            titleInput.setText(task.name)
            dateInput.setText(DateTimeAdapter.toString(task.date, DateTimeAdapter.DATE))
            timeInput.setText(DateTimeAdapter.toString(task.date, DateTimeAdapter.TIME))
            contentInput.setText(task.desc)
        }
    }

    private fun initPickers() {
        binding.apply {
            formTitle.text = if (type == OperationType.ADD) resources.getString(R.string.create_title) else resources.getString(
                R.string.edit_title)
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .build()

            datePicker.addOnPositiveButtonClickListener {
                dataText = DateTimeAdapter.toString(Date(it), DateTimeAdapter.DATE)
                dateInput.setText(dataText)
            }

            timePicker.addOnPositiveButtonClickListener {
                val hours = timePicker.hour
                val minutes = timePicker.minute
                val textH = if (hours < 10) "0$hours" else "$hours"
                val textM = if (minutes < 10) "0$minutes" else "$minutes"
                timeText = "$textH:$textM"
                timeInput.setText(timeText)
            }

            date.setEndIconOnClickListener {
                datePicker.show(supportFragmentManager, "tag")
            }
            time.setEndIconOnClickListener {
                timePicker.show(supportFragmentManager, "tag")
            }
        }
    }
}