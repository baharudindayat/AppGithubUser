package com.baharudindayat.appgithubuser.ui.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.appgithubuser.data.remote.response.FollowersItem
import com.baharudindayat.appgithubuser.databinding.FragmentFollowBinding
import com.baharudindayat.appgithubuser.ui.adapter.FollowerAdapter
import com.baharudindayat.appgithubuser.ui.viewmodel.FollowersViewModel


class FollowerFragment : Fragment() {

    private val followerViewModel by viewModels<FollowersViewModel>()
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

        val username = requireActivity().intent.getStringExtra("USERNAME").toString()

        followerViewModel.getFollowers(username)

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followerViewModel.followers.observe(viewLifecycleOwner) { followers ->
            setFollowerData(followers)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollowerData(follower: List<FollowersItem>) {
        val adapterFollower = FollowerAdapter(follower)
        binding?.rvFollow?.adapter = adapterFollower
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}