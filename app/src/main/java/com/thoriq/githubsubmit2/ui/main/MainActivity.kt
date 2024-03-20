package com.thoriq.githubsubmit2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thoriq.githubsubmit2.R
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.databinding.ActivityMainBinding
import com.thoriq.githubsubmit2.ui.DarkMode
import com.thoriq.githubsubmit2.ui.ModeViewModel
import com.thoriq.githubsubmit2.ui.ViewAdapter
import com.thoriq.githubsubmit2.ui.detail.UserUiState
import com.thoriq.githubsubmit2.ui.ViewModelFactory
import com.thoriq.githubsubmit2.ui.favorite.FavoriteUserActivity

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private val mainViewModel by viewModels<MainViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    private val settingViewModel by viewModels<ModeViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener(this)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchUser(searchView.text.toString())
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.uiState.observe(this) { uiState ->

            when (uiState){

                is UserUiState.Loading -> {

                    showLoading(true)

                }

                is UserUiState.Success -> {

                    setReviewData(uiState.data)

                    showLoading(false)

                }

                is UserUiState.Error -> {

                    Toast.makeText(this, uiState.error, Toast.LENGTH_SHORT).show()

                    showLoading(false)

                }

            }

        }

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }

    private fun setReviewData(consumerReviews: List<ItemsItem?>) {
        val adapter = ViewAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.favoritelist -> {
                val intFav = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intFav)
            }
            R.id.setting -> {
                val intMode = Intent(this@MainActivity, DarkMode::class.java)
                startActivity(intMode)
            }
        }
        return true
    }


}