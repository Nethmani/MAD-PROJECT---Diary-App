package com.example.notessqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize views
        val save = findViewById<Button>(R.id.button3)
        val load = findViewById<Button>(R.id.button4)
        val editText = findViewById<EditText>(R.id.Edittext)
        val editText2 = findViewById<EditText>(R.id.edittext2)
        val login = findViewById<Button>(R.id.login)
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        // Save button click listener
        save.setOnClickListener {
            // Retrieve user input
            val userName = editText.text.toString()
            val pass = editText2.text.toString()

            // Validate input (check if fields are empty)
            if (userName.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save data to SharedPreferences
            val editor = sharedPref.edit()
            editor.putString("user_name", userName)
            editor.putString("password", pass)
            editor.apply()
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        }

        // Load button click listener
        load.setOnClickListener {
            // Retrieve data from SharedPreferences
            val userName = sharedPref.getString("user_name", null)
            val pass = sharedPref.getString("password", null)

            // Set retrieved data to EditText fields
            editText.setText(userName)
            editText2.setText(pass)

            Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show()
        }

        // Login button click listener
        login.setOnClickListener {
            // Retrieve user input
            val enteredUserName = editText.text.toString()
            val enteredPassword = editText2.text.toString()

            // Retrieve saved data from SharedPreferences
            val savedUserName = sharedPref.getString("user_name", null)
            val savedPassword = sharedPref.getString("password", null)

            // Perform validation (for example, check if username and password match)
            if (enteredUserName == savedUserName && enteredPassword == savedPassword) {
                // If credentials are correct, navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Finish the LoginActivity to prevent going back to it on back press
            } else {
                // If credentials are incorrect, display error message
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
