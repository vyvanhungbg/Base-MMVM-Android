package com.hungvv.di

import com.hungvv.local_storage.key_value.LocalKV
import org.koin.dsl.module


/**
- Create by :Vy Hùng
- Create at :31,October,2023
 **/

val localStorageKeyValue = module {
    single { LocalKV.provideKeyValueStorage(get(), true) }
}

