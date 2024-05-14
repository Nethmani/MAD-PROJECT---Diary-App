package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NotesDatabaseHelper(this)


        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            // Validate input data
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please enter both title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (title.length > 25) { // Adjust the maximum length as needed
                Toast.makeText(this, "Title is too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (content.length > 500) { // Adjust the maximum length as needed
                Toast.makeText(this, "Content is too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(0, title, content) // Do not pass id here
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }

    }
}