package com.example.foxnotemini.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foxnotemini.R
import com.example.foxnotemini.activities.MainActivity
import com.example.foxnotemini.adapters.RecyclerViewAdapter
import com.example.foxnotemini.database.Note
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteList.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.viewModelScope.launch {
            var adapter: RecyclerViewAdapter
            var notes: List<Note> = emptyList()
            try {
                notes = noteViewModel.notes.await()
            } catch (e: Exception) {
                var text = e.message
                var duration = Toast.LENGTH_LONG
                Toast.makeText(requireContext(), text, duration).show()
            }
            adapter = RecyclerViewAdapter(
                notes,
                { clikedNote ->
                    val newNoteFragment = NoteFragment()
                    val args = Bundle().apply {
                        if(clikedNote.id != null) {
                            putInt("NOTE_ID", clikedNote.id)
                        }
                    }
                    newNoteFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, newNoteFragment)
                        commitNow()
                    }
                }
            )
            binding.noteList.adapter = adapter
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
}