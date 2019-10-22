package com.project.movieapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.project.movieapp.beans.ProductionCompany
import com.project.movieapp.extensions.loadUrl


class CompanyListAdapter(var context: Context, private val companyList :ArrayList<ProductionCompany>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int  = companyList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image = ImageView(context)
        image.loadUrl("")
        container.addView(image)
        return image
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
//        super.destroyItem(container, position, `object`)
    }
}