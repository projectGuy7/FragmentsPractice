package com.example.fragmentspractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentspractice.databinding.FragmentColorfulBinding

const val COLOR_KEY = "COLOR_KEY"

class ColorfulFragment : Fragment() {
    var color: Int? = null
        private set
    private var _binding: FragmentColorfulBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        color = arguments?.getInt(COLOR_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColorfulBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("COLOR", "$color")
        color?.let { binding.root.setBackgroundColor(it or 0xFF000000.toInt()) }
        binding.fragmentName.text =
            if (tag == SECOND_FRAGMENT_TAG) getString(R.string.second_fragment) else getString(R.string.third_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        color?.let { outState.putInt(COLOR_KEY, it) }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(color: Int?) =
            ColorfulFragment().apply {
                arguments = Bundle().apply {
                    color?.let { putInt(COLOR_KEY, it) }
                }
            }
    }
}