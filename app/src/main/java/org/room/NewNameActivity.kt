package org.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewNameActivity : AppCompatActivity() {

    private lateinit var editFirstNameView: EditText
    private lateinit var editLastNameView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_name)
        editFirstNameView = findViewById(R.id.edit_first_name)
        editLastNameView = findViewById(R.id.edit_last_name)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editFirstNameView.text) || TextUtils.isEmpty(editLastNameView.text) ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val firstName = editFirstNameView.text.toString()
                val lastName = editLastNameView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, arrayOf(firstName, lastName))
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "org.room.REPLY"
    }

}