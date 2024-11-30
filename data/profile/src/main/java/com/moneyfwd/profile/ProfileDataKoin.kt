package com.moneyfwd.profile

import com.moneyfwd.domain.profile.repository.ProfileRepository
import com.moneyfwd.profile.network.ProfileApiService
import com.moneyfwd.profile.repository.ProfileRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

object ProfileDataKoin {
    val module = module {
        single {
            get<Retrofit>().create<ProfileApiService>()
        }
        factory<ProfileRepository> {
            ProfileRepositoryImpl(apiService = get())
        }
    }
}