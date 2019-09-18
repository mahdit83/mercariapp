package com.mercari.android.MVVM.features.terminalnumber

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mercari.android.MVVM.features.home.HomeFragmentDirections

import com.mercari.android.R
import kotlinx.android.synthetic.main.terminal_number_fragment.*

class TerminalNumberFragment : Fragment() {

    companion object {
        fun newInstance() = TerminalNumberFragment()
    }

    private lateinit var viewModel: TerminalNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.terminal_number_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TerminalNumberViewModel::class.java)
        initializeUi()
    }

    private fun initializeUi() {

        val action = TerminalNumberFragmentDirections.actionTerminalNumberFragmentToTerminalDataConfirmFragment()

        button.setOnClickListener {
            findNavController().navigate(action)
        }


    }

}
