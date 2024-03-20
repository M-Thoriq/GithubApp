package com.thoriq.githubsubmit2.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thoriq.githubsubmit2.R
import com.thoriq.githubsubmit2.data.local.FavoriteUser
import com.thoriq.githubsubmit2.databinding.ActivityDetailUserBinding
import com.thoriq.githubsubmit2.ui.ViewModelFactory
import com.thoriq.githubsubmit2.ui.favorite.FavoriteViewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    private var isFavorited: Boolean = false

    private lateinit var favUser: FavoriteUser

    private val detailViewModel by viewModels<DetailUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)



        changeImage()

        detailViewModel.isLoading.observe(this) {
            detailViewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        if (intent.getStringExtra(EXTRA_USERNAME) != null) {

            binding.floatingActionButton.setOnClickListener {
                if(isFavorited){
                    detailViewModel.delete(favUser)
                    Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                } else {
                    detailViewModel.insert(favUser)
                    Toast.makeText(this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                }

                changeImage()
            }

            val username = intent.getStringExtra(EXTRA_USERNAME)
            Log.d("DETAIL_ACTIVITY", "$username")
            detailViewModel.getDetailByUsername(username.toString())

            detailViewModel.getFavoriteUser(username.toString()).observe(this){ favUser ->

                if (favUser != null){

                    binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_24)

                    isFavorited = true

                } else {

                    binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_border_24)

                    isFavorited = false

                }

            }

            detailViewModel.listReview.observe(this) { user ->
                Glide.with(this)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.ivAvatar)
                binding.tvName.text = user.name.toString()
                favUser = FavoriteUser(usrname = user.login.toString(), avatarUrl = user.avatarUrl)
                binding.tvUsername.text = user.login.toString()
                binding.tvFollowers.text = "${user.followers.toString()} Followers"
                binding.tvFollowing.text = "${user.following.toString()} Following"



                changeImage()
            }

            changeImage()

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = username.toString()
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }

    }

    companion object{
        const val EXTRA_USERNAME = "user.name"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    fun changeImage(){
        if (isFavorited){
            binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_24)
        }
        else {
            binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_border_24)

        }
    }

}