package com.example.foxnotemini

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foxnotemini.database.NoteEvent
import com.example.foxnotemini.database.NoteViewModel
import com.example.foxnotemini.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    //TODO dependency injection
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val overviewFragment = OverviewFragment()
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            if (binding.titleText.text.isNotEmpty() && binding.contentText.text.isNotEmpty()){
                noteViewModel.OnEvent(NoteEvent.SetTitle(binding.titleText.text.toString()))
                noteViewModel.OnEvent(NoteEvent.SetContent(binding.contentText.text.toString()))
                noteViewModel.OnEvent(NoteEvent.SaveNote)
            }

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, overviewFragment)
                commitNow()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}