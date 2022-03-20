package com.example.crudfirebasekita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class Show : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Books>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("BOOKS")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val book = h.getValue(Books::class.java)
                        list.add(book!!)
                    }
                    val adapter = Adapter(this@Show,R.layout.books,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}