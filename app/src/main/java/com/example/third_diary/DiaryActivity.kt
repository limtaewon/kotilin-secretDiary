package com.example.third_diary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val diaryText : EditText by lazy{
        findViewById(R.id.diaryText)
    }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEdit = getSharedPreferences("diary", Context.MODE_PRIVATE)
        diaryText.setText(diaryEdit.getString("detail",""))

        val runnable = Runnable{
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail",diaryText.text.toString())
            }
        }

        diaryText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }
    }
}