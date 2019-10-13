package com.project.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.movieapp.adapter.PopularListAdapter
import com.project.movieapp.extensions.shortToast
import com.project.movieapp.web.ResponseBean
import com.project.movieapp.web.WebClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPopular()

        popularRecyclerView.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = GridLayoutManager(this@MainActivity,2)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }


    private fun getPopular() {
        WebClient().popularService().getPopular().enqueue(object : Callback<ResponseBean> {
            override fun onResponse(call: Call<ResponseBean>, response: Response<ResponseBean>) {
                response.body()?.run {
                    popularRecyclerView.adapter =
                        PopularListAdapter(this@MainActivity,results)
                }

            }

            override fun onFailure(call: Call<ResponseBean>, t: Throwable) {
//                fixme warning
                this@MainActivity.shortToast(t.localizedMessage)
            }


        })
    }
}
