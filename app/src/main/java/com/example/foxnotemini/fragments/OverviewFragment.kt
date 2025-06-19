package com.example.foxnotemini.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.launch
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foxnotemini.R
import com.example.foxnotemini.activities.MainActivity
import com.example.foxnotemini.adapters.RecyclerViewAdapter
import com.example.foxnotemini.database.Note
import com.example.foxnotemini.database.NoteEvent
import com.example.foxnotemini.database.NoteViewModel
import com.example.foxnotemini.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    fun setUpRecyclerView() {
        adapter = RecyclerViewAdapter(
            emptyList(),
            { clickedNote ->
                val newNoteFragment = NoteFragment()
                val args = Bundle().apply {
                    if (clickedNote.id != null) {
                        putInt("NOTE_ID", clickedNote.id)
                    }
                }
                newNoteFragment.arguments = args
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

    fun onDeleteNoteButtonClicked(note: Note) {
        noteViewModel.OnEvent(NoteEvent.DeleteNote(note))
    }
}