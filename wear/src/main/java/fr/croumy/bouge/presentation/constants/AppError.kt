package fr.croumy.bouge.presentation.constants

import fr.croumy.bouge.R

abstract class AppError(message: Int): Exception(message.toString()) {
    val stringRes: Int = message

    object NotEnoughCredits: AppError(R.string.shop_error_not_enough_credit)
}