package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            // Validate input data
            if (newTitle.isEmpty() || newContent.isEmpty()) {
                Toast.makeText(this, "Please enter both title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newTitle.length > 25) { // Adjust the maximum length as needed
                Toast.makeText(this, "Title is too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newContent.length > 500) { // Adjust the maximum length as needed
                Toast.makeText(this, "Content is too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create updated note object
            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)

            // Finish activity and display toast message
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
