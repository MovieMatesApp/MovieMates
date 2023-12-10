package com.example.moviemates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view=inflater.inflate(R.layout.account_fragment,container,false)
        //val textView: TextView =view.findViewById(R.id.textViewHome)
        // Customize the fragment view as needed
        return view
    }
}
