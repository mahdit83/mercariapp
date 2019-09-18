package com.mercari.android.MVVM.features.terminalconfirm

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mercari.android.R

class TerminalDataConfirmFragment : Fragment() {

    companion object {
        fun newInstance() = TerminalDataConfirmFragment()
    }

    private lateinit var viewModel: TerminalDataConfirmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.terminal_data_confirm_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TerminalDataConfirmViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
