package fr.croumy.bouge.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.models.Direction
import fr.croumy.bouge.services.CompanionService
import java.util.Timer
import java.util.TimerTask

class MainViewModel(
    val companionService: CompanionService
): ViewModel() {
    val currentDrops = companionService.currentDrops

    val direction = mutableStateOf(Direction.RIGHT)
    val moveValue = mutableStateOf(0f)


    init {
        moveTimer()
        randomDropTimer()
    }

    private fun moveTimer() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                direction.value = Direction.random()

                moveValue.value = when (direction.value) {
                    Direction.LEFT -> {
                        if(moveValue.value <= -Window.WIDTH) Window.WIDTH.toFloat()
                        else moveValue.value - 10f
                    }
                    Direction.RIGHT -> {
                        if(moveValue.value >= Window.WIDTH) -Window.WIDTH.toFloat()
                        else moveValue.value + 10f
                    }
                }
            }
        }, 0, 800) // 800 == AnimatedSprite animationDuration
    }

    private fun randomDropTimer() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                randomDrop()
            }
        }, 5000, 5000)
    }

    private fun randomDrop() {
        val drops = FoodItem.allFood.mapNotNull { item ->
            val random = (0..item.dropChance).random()

            if (random == 0) item
            else null
        }

        if (drops.isNotEmpty()) {
            val drop = drops.maxBy { it.dropChance }
            companionService.currentDrops.value = companionService.currentDrops.value.plus(drop)

            println("Dropped: ${drop::class.java.name}")
        }
    }
}