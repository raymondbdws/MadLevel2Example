package com.rayray.madlevel2example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rayray.madlevel2example.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REMINDERS = arrayListOf<Reminder>()
    private val REMINDER_ADAPTER = ReminderAdapter(REMINDERS)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.btnAddReminder.setOnClickListener {
            val reminder = binding.etReminder.text.toString()
            addReminder(reminder)
        }

        binding.rvReminders.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvReminders.adapter = REMINDER_ADAPTER

        binding.rvReminders.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

    private fun addReminder(reminder: String) {
        if (reminder.isNotBlank()) {
            REMINDERS.add(Reminder(reminder))
            REMINDER_ADAPTER.notifyDataSetChanged()
            binding.etReminder.text?.clear()
        } else {
           Snackbar.make(etReminder, "U must fill something", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper{
        val CALLBACK = object: ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                REMINDERS.removeAt(position)
                REMINDER_ADAPTER.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(CALLBACK)
    }
}