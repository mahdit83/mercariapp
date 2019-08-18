package com.mercari.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.mercari.android.MVVM.base.BaseMVVMFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment(BaseMVVMFragment())
    }

    private fun startFragment(fragment: Fragment) {

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.commit()
    }
}
