package com.example.firestore_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var saveButton: Button
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var KEY_TITLE= "title"
    private var KEY_DESCRIPTION="description"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        saveButton = findViewById(R.id.button_save)

        saveButton.setOnClickListener {

                Toast.makeText(this, "BUTTON WORKS", Toast.LENGTH_SHORT).show()


            save()
        }

    }

    fun save() {

        Toast.makeText(this, "SAVE CLICKED", Toast.LENGTH_SHORT).show()

        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()

        val note=mutableMapOf<String, Any>()
        note.put(KEY_TITLE,title)
        note.put(KEY_DESCRIPTION,description)

        db.collection("Collection").
        document("My first note")
            .set(note)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "SUCCESS")
                Toast.makeText(this@MainActivity, "Note Added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE", "FAILURE", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }

    }
}