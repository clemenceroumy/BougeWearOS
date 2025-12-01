package fr.croumy.bouge.injection

import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.ui.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    singleOf(::BleScanner)

    viewModel { MainViewModel() }
}