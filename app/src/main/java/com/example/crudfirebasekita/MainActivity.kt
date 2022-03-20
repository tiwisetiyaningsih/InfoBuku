package com.example.crudfirebasekita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("BOOKS")

        btnSave.setOnClickListener {
            savedata()
            val intent = Intent (this, Show::class.java)
            startActivity(intent)
        }
    }
    private fun savedata() {
        val judul = inputJudul.text.toString()
        val detailinfo = inputInfo.text.toString()
        val bookId = ref.push().key.toString()
        val book = Books(bookId,judul,detailinfo)


        ref.child(bookId).setValue(book).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            inputJudul.setText("")
            inputInfo.setText("")
        }
    }

}