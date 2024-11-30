package com.moneyfwd.githubusers.application

import com.moneyfwd.data.search.SearchDataKoin
import com.moneyfwd.domain.DomainKoin
import com.moneyfwd.features.profile.ProfileFeatureKoin
import com.moneyfwd.features.search.SearchFeatureKoin
import com.moneyfwd.githubusers.navigation.NavigationKoin
import com.moneyfwd.githubusers.network.NetworkKoin
import com.moneyfwd.profile.ProfileDataKoin

object KoinGraph {
    internal fun allModules() = listOf(
        *featureModules(),
        *dataModules(),
        DomainKoin.module,
        NavigationKoin.module,
        NetworkKoin.module
    )

    private fun featureModules() = arrayOf(
        SearchFeatureKoin.module,
        ProfileFeatureKoin.module
    )

    private fun dataModules() = arrayOf(
        SearchDataKoin.module,
        ProfileDataKoin.module
    )
}