package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.usecase.image.ImageStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.impl.image.ImageStatusUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.post.PostStatusUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.register.RegisterAccountUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.setting.SettingStatusUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.setting.SettingStatusUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostStatusUseCase> { PostStatusUseCaseImpl(get()) }
  factory<RegisterAccountUseCase> { RegisterAccountUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
  factory<SettingStatusUseCase> {SettingStatusUseCaseImpl(get())}
  factory<ImageStatusUseCase> { ImageStatusUseCaseImpl(get()) }
}
