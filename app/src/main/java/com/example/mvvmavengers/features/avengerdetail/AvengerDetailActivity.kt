package com.example.mvvmavengers.features.avengerdetail

import android.os.Bundle
import com.example.mvvmavengers.base.presentation.BaseActivity
import com.example.mvvmavengers.features.avengerslist.domain.entities.Avenger
import com.example.mvvmavengers.databinding.ActivityAvengerDetailBinding
import com.example.mvvmavengers.utils.Constants.AVENGER_KEY
import com.example.mvvmavengers.utils.loadUrl

class AvengerDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityAvengerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAvengerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (intent.hasExtra(AVENGER_KEY)) {
            setValues(intent.getParcelableExtra(AVENGER_KEY))
        }
    }

    private fun setValues(avengerModel: Avenger?) {
        avengerModel?.let {
            title = it.avengerName

            binding.detailDate.text = it.avengerDateUpdate
            binding.detailTitle.text = it.avengerName
            binding.avengerDetail.text = it.description
            binding.detailImage.loadUrl(it.imageUrl)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
