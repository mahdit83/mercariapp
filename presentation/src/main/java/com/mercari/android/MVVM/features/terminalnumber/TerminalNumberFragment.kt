package com.mercari.android.MVVM.features.terminalnumber

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mercari.android.R

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
        // TODO: Use the ViewModel
    }

}
