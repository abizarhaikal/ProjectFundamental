package com.example.lastproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastproject.SettingPreferences
import com.example.lastproject.adapter.FavoriteAdapter
import com.example.lastproject.adapter.FavoriteViewModel
import com.example.lastproject.dataStore
import com.example.lastproject.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    
    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var adapterFavorite : FavoriteAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(requireActivity().application, pref = SettingPreferences.getInstance(requireActivity().dataStore))
    }
    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).supportActionBar?.title = "Favorite"
        adapterFavorite = FavoriteAdapter()
//        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFav.setHasFixedSize(true)
        favoriteViewModel.getAllUser().observe(requireActivity()) {userList ->
            if (userList != null) {
                binding.rvFav.adapter = adapterFavorite
                adapterFavorite.setListGit(userList)
            }
        }
    }


    
}