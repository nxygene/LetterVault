package com.sberkozd.lettervault.ui.grid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.adapter.GridAdapter
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.FragmentGridBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GridFragment : Fragment(R.layout.fragment_grid) {

    private val gridViewModel: GridViewModel by viewModels()

    private var _binding: FragmentGridBinding? = null

    private val binding get() = _binding!!

    private var gridLayoutManager: GridLayoutManager? = null

    private var recyclerView: RecyclerView? = null

    private var gridAdapter: GridAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGridBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        gridAdapter = GridAdapter()
        recyclerView = binding.gridFragmentRV
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = gridAdapter

        gridViewModel.noteList.observe(viewLifecycleOwner, {
            gridAdapter?.setItems(it)
        })

        binding.gridFragmentFab.setOnClickListener {
            findNavController().navigate(GridFragmentDirections.actionGridFragmentToAddFragment())
        }


    }

    fun onUpdate(id: Int, note: Note) {
        gridViewModel.updateNote(id, note)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}