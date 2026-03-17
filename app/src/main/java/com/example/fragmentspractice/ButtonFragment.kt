package com.example.fragmentspractice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentspractice.databinding.FragmentButtonsBinding


class ButtonFragment : Fragment() {
    private var _binding: FragmentButtonsBinding? = null
    private val binding get() = _binding!!
    private var communicator: ButtonFragmentCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as? ButtonFragmentCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.changeColorButton.setOnClickListener {
            communicator?.onChangeColorClicked()
        }
        binding.swapFragmentButton.setOnClickListener {
            communicator?.onSwapFragmentClicked()
        }
    }

    interface ButtonFragmentCommunicator {

        fun onChangeColorClicked()

        fun onSwapFragmentClicked()

    }
}