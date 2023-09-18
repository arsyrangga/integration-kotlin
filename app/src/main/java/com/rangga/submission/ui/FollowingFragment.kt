package com.rangga.submission.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangga.submission.ListDetailAdapter
import com.rangga.submission.data.Account
import com.rangga.submission.data.response.FollowersResponse
import com.rangga.submission.data.retrofit.ApiRequest
import com.rangga.submission.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var rvAccountDetail: RecyclerView
    private val list = ArrayList<Account>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        rvAccountDetail = binding.rvUserDetail
        getAccountFollowers()
        return binding.root
    }

    private fun getAccountFollowers() {
        val username = activity?.intent?.extras?.getString("username").toString()
        val call = ApiRequest.getApiService().getUserFollowing(username)
        binding.progressBar.visibility = View.INVISIBLE

        call.enqueue(object : Callback<List<FollowersResponse>> {
            override fun onResponse(
                call: Call<List<FollowersResponse>>,
                response: Response<List<FollowersResponse>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.forEach() {
                        list.add(
                            Account(
                            it.login,
                            it.login,
                            it.avatarUrl
                        )
                        )
                    }
                    showRecyclerList()
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }

            override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun showRecyclerList() {
        rvAccountDetail.layoutManager = GridLayoutManager(context, 1)
        val listDetailAdapter = ListDetailAdapter(list)
        rvAccountDetail.adapter = listDetailAdapter

        listDetailAdapter.setOnItemClickCallback(object : ListDetailAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Account) {
            }
        })
    }
}