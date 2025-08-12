package fr.croumy.bouge.presentation.usecases

interface IUseCase<Params, T : Any> {
    operator fun invoke(params: Params? = null): T
}