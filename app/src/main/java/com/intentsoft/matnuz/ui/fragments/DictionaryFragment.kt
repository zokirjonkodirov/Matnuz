package com.intentsoft.matnuz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.intentsoft.matnuz.adapters.DictionaryAdapter
import com.intentsoft.matnuz.databinding.FragmentDictionaryBinding
import com.intentsoft.matnuz.databinding.FragmentEditBinding
import com.intentsoft.matnuz.models.Resource
import com.intentsoft.matnuz.ui.viewmodels.MatnViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var matnViewModel: MatnViewModel
    private lateinit var dictionaryAdapter: DictionaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matnViewModel = ViewModelProvider(this)[MatnViewModel::class.java]

        setupRecyclerView()

        binding.edSearch.addTextChangedListener { editable ->
            editable?.let {
                if(editable.toString().isNotEmpty()) {
                    matnViewModel.getDictionary(editable.toString())
                }
            }
        }

        matnViewModel.dictionaryList.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { dictionaryResponse ->
                        dictionaryAdapter.differ.submitList(dictionaryResponse)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        dictionaryAdapter = DictionaryAdapter()
        binding.rcWordList.apply {
            adapter = dictionaryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}