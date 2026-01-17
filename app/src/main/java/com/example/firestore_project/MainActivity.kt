package com.example.firestore_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var saveButton: Button
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef: DocumentReference = db.collection("Notebook").document("My first note")
    private lateinit var loadButton: Button
    private lateinit var textViewData: TextView
    private var KEY_TITLE = "title"
    private var KEY_DESCRIPTION = "description"
    private lateinit var updateTitleButton: Button
    private lateinit var deleteDescriptionButton:Button
    private lateinit var deleteNoteButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        saveButton = findViewById(R.id.button_save)
        loadButton = findViewById(R.id.load_button)
        textViewData = findViewById(R.id.text_view_data)
        updateTitleButton = findViewById(R.id.button_update_title)
        deleteNoteButton = findViewById(R.id.button_delete_note)
        deleteDescriptionButton = findViewById(R.id.button_delete_description)

        saveButton.setOnClickListener {
            Toast.makeText(this, "BUTTON WORKS", Toast.LENGTH_SHORT).show()
            save()
        }

        loadButton.setOnClickListener {
            loadData()
        }
        updateTitleButton.setOnClickListener {
            updateTitle()
        }
        deleteDescriptionButton.setOnClickListener {
            deleteDescription()
        }
        deleteNoteButton.setOnClickListener {
            deleteNote()
        }

    }

    override fun onStart() {
        super.onStart()

       docRef.addSnapshotListener(this) { document, error ->
            error?.let {
                return@addSnapshotListener
            }
            document?.let {
                if (document.exists()) {
                    val title = document.getString(KEY_TITLE)
                    val description = document.getString(KEY_DESCRIPTION)

                    textViewData.text = "Title: $title\nDescription: $description"
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "the document does not exist",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }



    fun save() {

        Toast.makeText(this, "SAVE CLICKED", Toast.LENGTH_SHORT).show()

        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()

        val note = mutableMapOf<String, Any>()
        note.put(KEY_TITLE, title)
        note.put(KEY_DESCRIPTION, description)


        docRef.set(note)
            .addOnSuccessListener {
                Log.d("FIRESTORE", "SUCCESS")
                Toast.makeText(this@MainActivity, "Note Added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE", "FAILURE", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }

    }

    private fun loadData() {
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val title = document.getString(KEY_TITLE)
                val description = document.getString(KEY_DESCRIPTION)

//            val note=mutableMapOf<String, Any>()
//            note.put(KEY_TITLE,title!!)
//            note.put(KEY_DESCRIPTION,description!!)
                textViewData.text = "Title: " + "$title\nDescription: $description"
            } else {
                textViewData.text=""
                Toast.makeText(this@MainActivity, "the document does not exist", Toast.LENGTH_SHORT)
                    .show()

            }

        }.addOnFailureListener {
            Toast.makeText(this@MainActivity, "failed to load data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTitle(){
        val title=editTextTitle.text.toString()
        val note=mutableMapOf<String, Any>()
        note[KEY_TITLE]=title
        docRef.set(note, SetOptions.merge())
    }
    private fun deleteDescription(){
        val note=mutableMapOf<String,Any>()
        note[KEY_DESCRIPTION]=FieldValue.delete()
        docRef.update(note)
    }
    private fun deleteNote(){
        docRef.delete()
    }
}