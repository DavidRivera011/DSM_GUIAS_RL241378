package com.example.recursos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recursos.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val price = intent.getStringExtra("price") ?: ""
        val imageName = intent.getStringExtra("imageName") ?: ""
        binding.tvName.text = name
        binding.tvDescription.text = description
        binding.tvPrice.text = price
        val resId = resources.getIdentifier(imageName, "drawable",
            packageName)
        if (resId != 0) binding.ivDish.setImageResource(resId)
        else
            binding.ivDish.setImageResource(R.drawable.ic_launcher_foreground)
    }
}