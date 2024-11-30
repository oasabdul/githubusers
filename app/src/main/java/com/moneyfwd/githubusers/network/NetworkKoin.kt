package com.moneyfwd.githubusers.network

import com.moneyfwd.githubusers.BuildConfig
import org.koin.dsl.module

object NetworkKoin {
    val module = module {
        factory {
            AuthInterceptor(accessToken =  BuildConfig.GITHUB_ACCESS_TOKEN)
        }
        factory {
            RetrofitFactory(authInterceptor = get()).invoke(BuildConfig.BASE_URL)
        }
    }
}