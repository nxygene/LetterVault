package com.sberkozd.lettervault.ui.grid

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.databinding.FragmentGridBinding
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.lettervault.adapter.GridAdapter
import com.sberkozd.lettervault.adapter.NoteAdapter
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.FragmentHomeBinding
import com.sberkozd.lettervault.ui.home.HomeFragmentDirections
import com.sberkozd.lettervault.ui.home.HomeViewModel
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
        setHasOptionsMenu(true)
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

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_grid, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.grid_menu_item_more -> {
                Toast.makeText(context, "To be implemented!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.grid_menu_item_back -> {
                findNavController().navigate(GridFragmentDirections.actionGridFragmentToHomeFragment())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
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