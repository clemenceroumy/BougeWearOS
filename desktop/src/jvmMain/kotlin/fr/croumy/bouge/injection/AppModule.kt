package fr.croumy.bouge.injection

import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.services.CompanionService
import fr.croumy.bouge.ui.main.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    singleOf(::CompanionService)
    singleOf(::BleScanner)

    viewModel { MainViewModel(get(), get()) }
}