package com.thoriq.githubsubmit2.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoriq.githubsubmit2.R
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.ui.ViewAdapter
import com.thoriq.githubsubmit2.ui.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FollowFragment : Fragment() {
    private lateinit var rvFollow: RecyclerView

    // TODO: Rename and change types of parameters
    private var position: Int? = null
    private var username: String? = null

    private val detailViewModel by viewModels<DetailUserViewModel>{

        ViewModelFactory.getInstance(requireActivity())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
            username = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollow = view.findViewById(R.id.rvFollow)

        if (position == 1){
            detailViewModel.getFollower(username.toString())
            detailViewModel.listFollower.observe(viewLifecycleOwner){
                setReviewData(it)
            }
        } else {
            detailViewModel.getFollowing(username.toString())
            detailViewModel.listFollowing.observe(viewLifecycleOwner){
                setReviewData(it)
            }
        }

        detailViewModel.isLoading.observe(requireActivity()) {
            detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setReviewData(consumerReviews: List<ItemsItem?>) {
        val adapter = ViewAdapter()
        adapter.submitList(consumerReviews)
        rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        rvFollow.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}