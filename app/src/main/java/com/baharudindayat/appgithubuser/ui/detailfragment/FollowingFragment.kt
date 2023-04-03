@file:Suppress("DEPRECATION")

package com.baharudindayat.appgithubuser.ui.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.appgithubuser.data.remote.response.FollowingItem
import com.baharudindayat.appgithubuser.data.remote.response.ItemsItem
import com.baharudindayat.appgithubuser.databinding.FragmentFollowBinding
import com.baharudindayat.appgithubuser.ui.adapter.FollowingAdapter
import com.baharudindayat.appgithubuser.ui.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private val followingViewModel by viewModels<FollowingViewModel>()

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvFollow?.addItemDecoration(itemDecoration)

        val dataUsername = requireActivity().intent.getParcelableExtra<ItemsItem>("USERNAME") as ItemsItem
        val username = dataUsername.login


        followingViewModel.getFollowing(username)

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.following.observe(viewLifecycleOwner) { following ->
            setFollowingData(following)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollowingData(following: List<FollowingItem>) {
        val adapterFollower = FollowingAdapter(following)
        binding?.rvFollow?.adapter = adapterFollower
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}