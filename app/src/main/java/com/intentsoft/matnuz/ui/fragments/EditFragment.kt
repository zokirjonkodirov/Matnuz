package com.intentsoft.matnuz.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.intentsoft.matnuz.R
import com.intentsoft.matnuz.databinding.FragmentEditBinding
import com.intentsoft.matnuz.models.Resource
import com.intentsoft.matnuz.models.Transliteration
import com.intentsoft.matnuz.ui.viewmodels.MatnViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var matnViewModel: MatnViewModel

    private var transiletaration = "latin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matnViewModel = ViewModelProvider(this)[MatnViewModel::class.java]

        binding.tvCheck.setOnClickListener {
            if (notEmpty()) {
                matnViewModel.postText(binding.etCheckText.text.toString(), transiletaration)
            }
        }

        binding.etCheckText.addTextChangedListener { editable ->
            binding.tvCounter.text = binding.etCheckText.text.length.toString()
            hideError()
            hideProgress()
            hideSuccess()
        }

        binding.btnClear.setOnClickListener {
            binding.imgCleared.visibility = View.VISIBLE
            binding.etCheckText.text.clear()
            binding.tvCounter.text = "0"
            lifecycleScope.launch {
                delay((2000))
                binding.imgCleared.visibility = View.GONE
            }
        }

        binding.btnCopy.setOnClickListener {
            binding.imgCopied.visibility = View.VISIBLE
            requireContext().copyToClipboard(binding.etCheckText.text.toString())
            lifecycleScope.launch {
                delay((2000))
                binding.imgCopied.visibility = View.GONE
            }
        }

        binding.segmentedGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.btnLotin -> {
                    transiletaration = "latin"
                    matnViewModel.changeToLatin(Transliteration(binding.etCheckText.text.toString()))
                }

                else -> {
                    transiletaration = "cyrillic"
                    matnViewModel.changeToCyrillic(Transliteration(binding.etCheckText.text.toString()))
                }
            }
        }

        matnViewModel.latin.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.etCheckText.setText(response.data)
                }

                is Resource.Error -> {
                    binding.etCheckText.setText(response.message)
                }
            }
        }

        matnViewModel.cyrillic.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.etCheckText.setText(response.data)
                }

                is Resource.Error -> {
                    binding.etCheckText.setText(response.message)
                }

                else -> {
                    binding.etCheckText.setText("Kutilmagan xatolik")
                }
            }
        }

        matnViewModel.correctData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    hideProgress()
                    if (response.data?.errors == false) {
                        showSuccess()
                    } else {
                        showError()
                        binding.tvErrorCount.text =
                            response.data?.data?.size.toString() + "ta xato"
                    }
                }

                is Resource.Error -> {
                    showError()
                }
            }
        }
    }

    private fun notEmpty(): Boolean {
        if (binding.etCheckText.text.isEmpty()) {
            return false
        }

        return true
    }

    private fun showProgress() {
        binding.anmLoading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.anmLoading.visibility = View.GONE
    }

    private fun showError() {
        binding.imgResultError.visibility = View.VISIBLE
        binding.tvErrorCount.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.imgResultError.visibility = View.GONE
        binding.tvErrorCount.visibility = View.GONE
    }

    private fun showSuccess() {
        binding.imgResultSuccess.visibility = View.VISIBLE
    }

    private fun hideSuccess() {
        binding.imgResultSuccess.visibility = View.GONE
    }

    private fun Context.copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip =
            ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }
}