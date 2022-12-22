package org.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newNameActivityRequestCode = 1
    private val personNameViewModel: PersonNameViewModel by viewModels {
        PersonNameViewModelFactory((application as PersonNamesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PersonNameListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNameActivity::class.java)
            startActivityForResult(intent, newNameActivityRequestCode)
        }


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        personNameViewModel.allPersonNames.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNameActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewNameActivity.EXTRA_REPLY)?.let { reply -> val personName = PersonName(reply[0], reply[1])
                personNameViewModel.insert(personName)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}