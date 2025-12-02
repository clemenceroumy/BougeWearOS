package fr.croumy.bouge.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fr.croumy.bouge.core.models.shop.food.FoodItem
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MainViewModel(): ViewModel() {
    val currentDrop = mutableStateOf<List<FoodItem>>(emptyList())

    init {
        randomDropTimer()
    }

    private fun randomDropTimer() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                println("Dropping ....")
                randomDrop()
            }
        }, 0, 5000)
    }

    private fun randomDrop() {
        val drops = FoodItem.allFood.mapNotNull { item ->
            val random = (0..item.dropChance).random()

            if (random == 0) item
            else null
        }

        if (drops.isNotEmpty()) currentDrop.value = currentDrop.value.plus(drops.maxBy { it.dropChance })
    }
}