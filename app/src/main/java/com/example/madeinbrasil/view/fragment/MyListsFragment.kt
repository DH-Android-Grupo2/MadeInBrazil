package com.example.madeinbrasil.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madeinbrasil.R
import com.example.madeinbrasil.adapter.MyListsAdapter
import com.example.madeinbrasil.databinding.FragmentMyListsBinding
import com.example.madeinbrasil.model.customLists.ListWithMedia
import com.example.madeinbrasil.utils.Constants.CustomLists.LIST
import com.example.madeinbrasil.view.activity.CreateListActivity
import com.example.madeinbrasil.view.activity.CustomListDetailsActivity
import com.example.madeinbrasil.viewModel.CustomListViewModel
import kotlinx.android.synthetic.main.fragment_my_lists.*


class MyListsFragment : Fragment() {
    private lateinit var binding: FragmentMyListsBinding
    private var actionMode: ActionMode? = null
    private lateinit var customListViewMovel: CustomListViewModel

    private val myListsAdapter by lazy {
        MyListsAdapter { list ->
            showListDetails(list)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myListsAdapter.onItemClick = {
            enableActionMode(it)
        }

        myListsAdapter.onItemLongClick = {
            enableActionMode(it)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyListsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customListViewMovel =  ViewModelProvider(this).get(CustomListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNewList.setOnClickListener {
            startCreateEditActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tvEmptyListMessage.visibility = View.GONE


        customListViewMovel.getListWithMedia(false)

        customListViewMovel.getListsSuccess.observe(this, { list ->

            pbGettingLists.visibility = View.GONE

            if(list.isEmpty()) {
                binding.tvEmptyListMessage.visibility = View.VISIBLE
            } else {
                binding.tvEmptyListMessage.visibility = View.GONE
                setupRecyclerView(list)
            }

        })

        customListViewMovel.listFailure.observe(this, { message ->

            pbGettingLists.visibility = View.GONE

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        })

//        customListViewMovel.getListsUni()
//
//        customListViewMovel.uniLists.observe(viewLifecycleOwner, {
//
//            binding.rvMyLists.apply {
//                layoutManager = LinearLayoutManager(this@MyListsFragment.context)
//                adapter = MyListsAdapter(it) { id ->
//                    val intent = Intent(this@MyListsFragment.context, CustomListDetailsActivity::class.java)
//                    intent.putExtra(LIST_ID, id)
//                    startActivity(intent)
//                }
//            }
//        })

    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null)
            actionMode = (activity as AppCompatActivity).startSupportActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_edit_delete, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    val item = menu?.findItem(R.id.action_edit)
                    item?.isVisible = (myListsAdapter.selectedItems.size == 1)
                    return true
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    when(item?.itemId) {
                        R.id.action_edit -> {
                            val list = myListsAdapter.getItem()
                            startCreateEditActivity(list)
                            mode?.finish()
                            return true
                        }
                        R.id.action_delete -> {
                            deleteLists(myListsAdapter.selectedItems)
                            myListsAdapter.deleteLists()
                            mode?.finish()
                            return true
                        }
                    }
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    myListsAdapter.let {
                        it.selectedPositions.clear()
                        it.selectedItems.clear()
                        it.notifyDataSetChanged()
                    }

                    actionMode = null
                }

            })

        myListsAdapter.tooglePosition(position)
        val size = myListsAdapter.selectedItems.size
        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = size.toString()
            actionMode?.invalidate()
        }

    }

    private fun setupRecyclerView(list: List<ListWithMedia>) {
        binding.rvMyLists.apply {
            layoutManager = LinearLayoutManager(this@MyListsFragment.context)
            myListsAdapter.list = list as MutableList<ListWithMedia>
            adapter = myListsAdapter
        }
    }

    private fun showListDetails(list: ListWithMedia) {
        val intent = Intent(this@MyListsFragment.context, CustomListDetailsActivity::class.java)
        intent.putExtra(LIST, list)
        startActivity(intent)
    }

    private fun startCreateEditActivity(list: ListWithMedia? = null) {
        val intent = Intent(context, CreateListActivity::class.java)
        list?.let {
            intent.putExtra(LIST, it)
        }
        startActivity(intent)
    }


    private fun deleteLists(selectedItems: List<String>) {
        customListViewMovel.deleteLists(selectedItems)

        customListViewMovel.listSucess.observe(this, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        customListViewMovel.listFailure.observe(this, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        const val CODE = 888
        const val LIST_ID = "list_id"
    }
}