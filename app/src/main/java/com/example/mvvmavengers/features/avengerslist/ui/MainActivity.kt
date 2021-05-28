package com.example.mvvmavengers.features.avengerslist.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmavengers.features.avengerslist.domain.entities.Avenger
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import com.example.mvvmavengers.features.avengerslist.domain.entities.Result
import com.example.mvvmavengers.base.presentation.BaseActivity
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.databinding.ActivityMainBinding
import com.example.mvvmavengers.features.avengerdetail.AvengerDetailActivity
import com.example.mvvmavengers.features.avengerslist.ui.adapter.AvengersListAdapter
import com.example.mvvmavengers.utils.Constants.AVENGER_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: AvengersListViewModel by viewModel()
    private val adapter by lazy { AvengersListAdapter { onAvengerListItemClicked(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createAvengersListAdapter()
        mainViewModel.state.observe(this, {
            manageState(it)
        })
    }

    private fun manageState(it: ResultAvenger<AvengersModel?>?) {
        when (it) {
            is ResultAvenger.Success -> {
                hideProgress()
                it.data?.data?.results?.let { it1 -> adapter.avengers = it1 }
            }

            is ResultAvenger.Error -> {
                hideProgress()
                showError(it.exception.toString())
            }

            is ResultAvenger.Loading -> {
                showProgress()
            }
        }
    }

    private fun createAvengersListAdapter() {
        binding.avengerListRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.avengerListRecyclerview.itemAnimator = DefaultItemAnimator()
        binding.avengerListRecyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.avengerListRecyclerview.adapter = adapter
    }

    private fun onAvengerListItemClicked(avengerModel: Result?) {
        avengerModel?.let {
            Timber.d("avenger name: %s", it.name)
            val intent = Intent(applicationContext, AvengerDetailActivity::class.java)
            val imageUrl = it.thumbnail?.path + "." + it.thumbnail?.extension

            intent.putExtra(AVENGER_KEY, Avenger(
                (it.name.orEmpty()),
                (it.modified.orEmpty()),
                (imageUrl),
                (it.description.orEmpty())
            ))

            startActivity(intent)
        }
    }
}