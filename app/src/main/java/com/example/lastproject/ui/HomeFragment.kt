package com.example.lastproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastproject.R
import com.example.lastproject.adapter.GitHubAdapter
import com.example.lastproject.data.remote.response.ItemsItem
import com.example.lastproject.databinding.FragmentHomeBinding
import com.example.lastproject.viewmodel.DetailViewModel
import com.example.lastproject.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: GitHubAdapter
    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater ,container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.github_user)
        adapter = GitHubAdapter()
        adapter.setOnItemClickCallback(object : GitHubAdapter.OnItemClickCallback {
            override fun onItemClicked(listItem: ItemsItem) {
                Toast.makeText(requireActivity() ,listItem.login ,Toast.LENGTH_SHORT).show()
                val userDetail =
                    ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

                val detailIntent = Intent(requireActivity() ,DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USERNAME ,listItem.login)
                startActivity(detailIntent)

            }

        })

//        val settingPreferences = SettingPreferences.getInstance(application.dataStore)
//        val isDarkModeActive = settingPreferences.getThemeSetting()

//        lifecycleScope.launch {
//            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
//                if (isDarkModeActive) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                }
//            }
//        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity() ,layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)


        homeViewModel.listItem.observe(requireActivity()) { item ->
            setListItem(item)
        }

        homeViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        homeViewModel.snackbarText.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let { snackbar ->
                Snackbar.make(
                    requireView() ,
                    snackbar ,
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }

        binding.searchView.editText.setOnFocusChangeListener { v ,hasFocus ->
            if (hasFocus) {
                (requireActivity() as MainActivity).hideBottomNavigation()
            } else {
                (requireActivity() as MainActivity).showBottomNavigation()
            }
        }


        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val userName = textView.text.toString()
                        val userView = ViewModelProvider(this@HomeFragment).get(HomeViewModel::class.java)
                        userView.findOne(userName)
                        searchView.hide()
                        true
                    } else {
                        false
                    }
                }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.item_menu,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.favorite -> {
//                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.setting -> {
//                val intent = Intent(this@MainActivity, SettingActivity::class.java)
//                startActivity(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


    private fun setListItem(itemList: List<ItemsItem>?) {
        adapter.submitList(itemList)
        binding.rvMain.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}