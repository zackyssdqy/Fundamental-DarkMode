package com.zacky.fundamentalsubmission.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zacky.fundamentalsubmission.R
import com.zacky.fundamentalsubmission.data.remote.response.ItemsItem
import com.zacky.fundamentalsubmission.data.remote.retrofit.ApiConfig
import com.zacky.fundamentalsubmission.databinding.FragmentFollBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {
    private lateinit var _binding: FragmentFollBinding
    private val binding get() = _binding

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = ""
        const val FOLLOWER = "jumlah_follower"
        const val FOLLOWING = "jumlah_following"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_foll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollBinding.bind(view)

        binding.rvFoll.layoutManager = LinearLayoutManager(requireActivity())

        val index = arguments?.getInt(ARG_POSITION, 0)
        val name = arguments?.getString(ARG_USERNAME)

        val follower = arguments?.getInt(FOLLOWER, 0)
        val following = arguments?.getString(FOLLOWING)

        if (index == 1) {
            getFollower(name!!)

        } else {
            getFollowing(name!!)
        }
    }


    private fun setReviewData(listGithub: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listGithub)
        binding.rvFoll.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getFollower(USERNAME: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowers(USERNAME)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(DetailActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e(DetailActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(USERNAME: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(USERNAME)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(DetailActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e(DetailActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}