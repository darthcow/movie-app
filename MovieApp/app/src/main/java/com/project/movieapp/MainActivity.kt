package com.project.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.project.movieapp.adapter.PopularListAdapter
import com.project.movieapp.extensions.longToast
import com.project.movieapp.beans.PopularResponseBean
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
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }


    private fun getPopular() {
        WebClient().movieService().getPopular().enqueue(object : Callback<PopularResponseBean> {
            override fun onResponse(
                call: Call<PopularResponseBean>,
                popularResponse: Response<PopularResponseBean>
            ) {
                with(popularResponse) {
                if (isSuccessful) {
                    body()?.run {
                        popularRecyclerView.adapter =
                            PopularListAdapter(this@MainActivity, results)
                    }
                } else {
                    this@MainActivity.longToast(message())
                }
            }
        }

                override fun onFailure(call: Call<PopularResponseBean>, t: Throwable) {
//                fixme warning
            this@MainActivity.longToast(t.message?: "unidentified error")
        }


    })
}
}
