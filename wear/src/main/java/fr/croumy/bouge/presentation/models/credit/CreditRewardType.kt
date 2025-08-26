package fr.croumy.bouge.presentation.models.credit

enum class CreditRewardType {
    WALK, // steps during a consecutive walk
    EXERCISE, // other exercise
    TOTAL_WALK, // total of steps at the end of the day
    BONUS_EXERCISE, // exercise done from notification
}