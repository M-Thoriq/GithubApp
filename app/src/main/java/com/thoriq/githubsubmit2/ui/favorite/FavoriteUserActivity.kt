package com.thoriq.githubsubmit2.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoriq.githubsubmit2.R
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.databinding.ActivityFavoriteUserBinding
import com.thoriq.githubsubmit2.ui.ViewAdapter
import com.thoriq.githubsubmit2.ui.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.getAllUsers().observe(this) { users ->

            val items = arrayListOf<ItemsItem>()

            users.map {

                val item = ItemsItem(login = it.usrname, avatarUrl = it.avatarUrl)

                items.add(item)

            }

            setViewData(items)
        }
    }

    private fun setViewData(view: List<ItemsItem?>) {
        val adapter = ViewAdapter()
        adapter.submitList(view)
        binding.rvFavorite.adapter = adapter
    }
}