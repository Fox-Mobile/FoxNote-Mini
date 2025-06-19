package com.example.foxnotemini.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.example.foxnotemini.R
import com.example.foxnotemini.database.Note
import com.example.foxnotemini.database.NoteEvent
import com.example.foxnotemini.database.NoteViewModel
import com.example.foxnotemini.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NoteFragment(private val note: Note? = null) : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val overviewFragment = OverviewFragment()
        super.onViewCreated(view, savedInstanceState)
        val noteId = note?.id

        if (note != null) {
            binding.titleText.setText(note.title)
            binding.contentText.setText(note.content)
        }

        binding.toolbar.setNavigationOnClickListener {
            if (binding.titleText.text.isNotEmpty() && binding.contentText.text.isNotEmpty()) {
                noteViewModel.OnEvent(NoteEvent.SetTitle(binding.titleText.text.toString()))
                noteViewModel.OnEvent(NoteEvent.SetContent(binding.contentText.text.toString()))
                noteViewModel.OnEvent(NoteEvent.SetID(noteId))
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
