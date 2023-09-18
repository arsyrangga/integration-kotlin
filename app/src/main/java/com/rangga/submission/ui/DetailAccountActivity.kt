package com.rangga.submission.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rangga.submission.R
import com.rangga.submission.SectionsPagerAdapter
import com.rangga.submission.data.response.DetailAccountResponse
import com.rangga.submission.data.retrofit.ApiRequest
import com.rangga.submission.databinding.ActivityDetailAccountBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAccountBinding


    companion object {
        const val Username = "username"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(Username)


        if(username != null){
            getAccountDetail(username)
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Detail User"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.followers)
                }
                1 -> {
                    tab.text = getString(R.string.following)
                }
            }
        }.attach()
    }

    private fun getAccountDetail(username:String) {
        val call = ApiRequest.getApiService().getDetailUser(username)
        binding.progressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<DetailAccountResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailAccountResponse>,
                response: Response<DetailAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Glide.with(applicationContext)
                        .load(data?.avatarUrl) // URL Gambar
                        .into(binding.profileUser)
                    binding.name.text = data?.name
                    binding.username.text = "@${data?.login}"
                    binding.followers.text = "${data?.followers.toString()} Followers"
                    binding.following.text = "${data?.following.toString()} Following"
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }

            override fun onFailure(call: Call<DetailAccountResponse>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }





}