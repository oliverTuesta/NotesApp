package com.example.notesapp

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        replaceFragment(HomeFragment(), true, fragmentManager)


    }

    companion object {

        fun replaceFragment(
            fragment: Fragment,
            isTransition: Boolean,
            fragmentManager: FragmentManager
        ) {

            val fragmentTransition = fragmentManager.beginTransaction()
            if (isTransition) {
                fragmentTransition.setCustomAnimations(
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left
                )
            }

            fragmentTransition.replace(R.id.frame_layout, fragment)
            fragmentTransition.addToBackStack(null)

            fragmentTransition.commit()

        }
    }
}