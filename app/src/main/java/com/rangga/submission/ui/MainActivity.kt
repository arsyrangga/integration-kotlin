package com.rangga.submission.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangga.submission.ListAccountAdapter
import com.rangga.submission.R
import com.rangga.submission.data.Account
import com.rangga.submission.data.response.ListAccountResponse
import com.rangga.submission.data.retrofit.ApiRequest
import com.rangga.submission.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAccount: RecyclerView


    private fun handleDetail(data:Account) {
        val intent = Intent(this, DetailAccountActivity::class.java)
        intent.putExtra(DetailAccountActivity.Username, data.id.toString())
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.INVISIBLE
        setContentView(binding.root)
        val actionbar = supportActionBar
        rvAccount = findViewById(R.id.rv_user)
        //set actionbar title
        actionbar!!.title = "Github Account Search"
        binding.textField.doAfterTextChanged {
            binding.buttonCari.isEnabled = it.toString().length >= 1
        }
        binding.buttonCari.setOnClickListener{
            getAccountList(binding.textField.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun getAccountList(text: String) {
        val call = ApiRequest.getApiService().getUser(text)
        binding.progressBar.visibility = View.VISIBLE
        val list = ArrayList<Account>()
        showRecyclerList(list)


        call.enqueue(object : Callback<ListAccountResponse> {
            override fun onResponse(
                call: Call<ListAccountResponse>,
                response: Response<ListAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data!!.items!!.forEach { item ->
                        list.add(Account(
                            item!!.login,
                            item.avatarUrl
                        ))
                    }
                    showRecyclerList(list)

                    binding.progressBar.visibility = View.INVISIBLE
                    // Log or print the data
                }
            }

            override fun onFailure(call: Call<ListAccountResponse>, t: Throwable) {
                Log.d("Retrogit", t.toString())
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Switching on the item id of the menu item
        val intent = Intent(this, DetailAccountActivity::class.java)
        intent.putExtra(DetailAccountActivity.Username, "arsyrangga")
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerList(list: List<Account>) {
        rvAccount.layoutManager = GridLayoutManager(this@MainActivity, 2)
        val listAccountAdapter = ListAccountAdapter(list)
        rvAccount.adapter = listAccountAdapter

        listAccountAdapter.setOnItemClickCallback(object : ListAccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Account) {
                handleDetail(data)
            }
        })
    }
}


