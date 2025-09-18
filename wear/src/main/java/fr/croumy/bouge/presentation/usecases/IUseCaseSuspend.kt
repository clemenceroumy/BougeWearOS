package fr.croumy.bouge.presentation.usecases

interface IUseCaseSuspend<Params, T : Any> {
    suspend operator fun invoke(params: Params? = null): T
}