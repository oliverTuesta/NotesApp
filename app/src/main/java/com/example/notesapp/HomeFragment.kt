package com.example.notesapp

import android.content.Context
import android.icu.lang.UCharacter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.NotesDatabase
import com.google.android.material.circularreveal.CircularRevealHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragment : BaseFragment() {

    private lateinit var myContext: FragmentActivity

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                try {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                    rvHome.adapter = NotesAdapter(notes)
                }catch (e: Exception){
                    println(". . . . . . . . . .")
                    println(e.message)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }


            }
        }

        val fabBtnCreateNote: FloatingActionButton = view.findViewById(R.id.fabBtnCreateNote)

        fabBtnCreateNote.setOnClickListener {
            MainActivity.replaceFragment(CreateNoteFragment(), true, myContext.supportFragmentManager)
        }

    }

}