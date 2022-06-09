package com.example.third_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    val numberPicker1 by lazy {
        findViewById<NumberPicker>(R.id.diaryLock1).apply {
            minValue = 0
            maxValue = 9
        }
    }
    val numberPicker2 by lazy {
        findViewById<NumberPicker>(R.id.diaryLock2).apply {
            minValue = 0
            maxValue = 9
        }
    }
    val numberPicker3 by lazy {
        findViewById<NumberPicker>(R.id.diaryLock3).apply {
            minValue = 0
            maxValue = 9
        }
    }


    val openButton: AppCompatButton by lazy { findViewById(R.id.openButton) }
    val changeButton: AppCompatButton by lazy { findViewById(R.id.changeButton) }

    var changeMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener{
            if(changeMode){
                Toast.makeText(this,"비밀번호변경중입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val userPassword = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(passwordPreference.getString("password","000").equals(userPassword)){
                startActivity(Intent(this,DiaryActivity::class.java))
            }
            else{
                errorMsg()
            }
        }
        changeButton.setOnClickListener{
            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val userPassword = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changeMode){
                passwordPreference.edit(commit = true) {
                    putString("password",userPassword)
                    changeMode=false
                    changeButton.setBackgroundColor(Color.BLACK)
                }
            }else{
                if(passwordPreference.getString("password","000").equals(userPassword)){
                    changeMode = true
                    it.setBackgroundColor(Color.RED)
                    Toast.makeText(this,"변경할 패스워드를 입력해 주세요",Toast.LENGTH_SHORT).show()
                }
                else{
                   errorMsg()
                }
            }
        }
    }
    private fun errorMsg(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못 입력되었습니다")
            .setPositiveButton("확인"){_,_ ->}
            .create()
            .show()
    }

}