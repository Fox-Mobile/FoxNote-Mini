package com.example.foxnotemini.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foxnotemini.R
import com.example.foxnotemini.adapters.RecyclerViewAdapter
import com.example.foxnotemini.database.Note
import com.example.foxnotemini.database.NoteEvent
import com.example.foxnotemini.database.NoteViewModel
import com.example.foxnotemini.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteViewModel.notes.collect { notes: List<Note> ->
                        adapter.updateData(notes)
                }
            }
        }

        binding.addNoteButton.setOnClickListener {
            val newNoteFragment = NoteFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, newNoteFragment)
                commitNow()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        adapter = RecyclerViewAdapter(
            emptyList(),
            { clickedNote ->
                val newNoteFragment = NoteFragment(clickedNote)
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, newNoteFragment)
                    commitNow()
                }
            },
            { deletedNote ->
                onDeleteNoteButtonClicked(deletedNote)
            }
        )

        binding.noteList.adapter = adapter
        binding.noteList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onDeleteNoteButtonClicked(note: Note) {
        noteViewModel.onEvent(NoteEvent.DeleteNote(note))
    }
}