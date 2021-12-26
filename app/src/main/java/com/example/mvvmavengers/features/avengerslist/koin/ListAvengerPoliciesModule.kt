package com.example.mvvmavengers.features.avengerslist.koin

import com.example.mvvmavengers.features.avengerslist.data.policy.ListAvengerRepositoryPolicy
import com.example.mvvmavengers.features.avengerslist.data.policy.impl.ListAvengerRepositoryCloudWithCachePolicyImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val ListAvengerPoliciesModule = module(override = true) {
    single<ListAvengerRepositoryPolicy> {
        ListAvengerRepositoryCloudWithCachePolicyImpl(listAvengerCloudDataSource = get(), listAvengerDiskDataSource = get())
    }
    // single<ListAvengerRepositoryPolicy> { ListAvengerRepositoryCacheWithCloudPolicyImpl(get(), get()) }
    // single<ListAvengerRepositoryPolicy> { ListAvengerRepositoryOnlyCloudPolicyImpl(get()) }
}
