package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {

    lateinit var currentDateTime: String

    lateinit var myContext: FragmentActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = activity as FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateCurrentTime()
        view.findViewById<TextView>(R.id.tvDateTime).text = currentDateTime
        imgDone.setOnClickListener {
            //save note
            saveNote()
        }

        imgBack.setOnClickListener {
            //save note
            MainActivity.replaceFragment(HomeFragment(), false, myContext.supportFragmentManager)
        }

    }

    private fun saveNote() {

        if (etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }

        if (etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }

        if (etNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = etNoteTitle.text.toString()
            notes.subTitle = etNoteSubTitle.text.toString()
            notes.noteText = etNoteDesc.text.toString()
            notes.dateTime = currentDateTime

            context.let {
                try {
                    NotesDatabase.getDatabase(it!!).noteDao().insertNotes(notes)
                }catch (e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }


                updateCurrentTime()
                tvDateTime.text = currentDateTime
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateCurrentTime() {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
        currentDateTime = sdf.format(Date())
    }
}