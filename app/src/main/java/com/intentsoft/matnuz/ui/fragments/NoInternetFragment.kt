package com.intentsoft.matnuz.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intentsoft.matnuz.databinding.FragmentNoInternetBinding

class NoInternetFragment : Fragment() {

    private var _binding: FragmentNoInternetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }
}