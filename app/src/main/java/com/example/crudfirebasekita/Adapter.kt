package com.example.crudfirebasekita

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Books> )
    : ArrayAdapter<Books>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textJudul = view.findViewById<TextView>(R.id.textJudul)
        val textInfo = view.findViewById<TextView>(R.id. textInfo)

        val BtnEdit = view.findViewById<ImageButton>(R.id.BtnEdit)
        val BtnDelete = view.findViewById<ImageButton>(R.id.BtnDelete)

        val book = list[position]

        textJudul.text = book.judul
        textInfo.text = book.detailinfo

        BtnEdit.setOnClickListener {
            showUpdateDialog(book)
        }
        BtnDelete.setOnClickListener {
            Deleteinfo(book)
        }

        return view

    }

    private fun showUpdateDialog(book: Books) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textJudul = view.findViewById<EditText>(R.id.textJudul)
        val textInfo = view.findViewById<EditText>(R.id.textInfo)

        textJudul.setText(book.judul)
        textInfo.setText(book.detailinfo)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbBooks = FirebaseDatabase.getInstance().getReference("BOOKS")

            val judul = textJudul.text.toString().trim()

            val detailinfo = textInfo.text.toString().trim()

            if (judul.isEmpty()){
                textJudul.error = "please enter name"
                textJudul.requestFocus()
                return@setPositiveButton
            }

            if (detailinfo.isEmpty()){
                textInfo.error = "please enter status"
                textInfo.requestFocus()
                return@setPositiveButton
            }

            val book = Books(book.id,judul,detailinfo)

            dbBooks.child(book.id).setValue(book).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()
    }
    private fun Deleteinfo(book: Books) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("BOOKS")
        mydatabase.child(book.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Show::class.java)
        context.startActivity(intent) }
    }