package fr.croumy.bouge.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.croumy.bouge.constants.Constants
import fr.croumy.bouge.constants.Window
import fr.croumy.bouge.core.models.shop.food.FoodItem
import fr.croumy.bouge.models.Direction
import fr.croumy.bouge.services.BleScanner
import fr.croumy.bouge.services.CompanionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Timer
import java.util.TimerTask

class MainViewModel(
    val companionService: CompanionService,
    val bleScanner: BleScanner
): ViewModel() {
    val currentDrops = companionService.currentDrops

    val direction = mutableStateOf(Direction.RIGHT)
    val moveValue = mutableStateOf(0f)

    val dropTimer = Timer()
    val moveTimer = Timer()

    /*init {
        moveTimer()
        randomDropTimer()
    }*/

    // TEMP, move to init
    fun onInit() {
        println("MainViewModel initialized")
        moveTimer()
        randomDropTimer()
    }

    // TEMP, move to onCleared
    fun onDispose() {
        println("MainViewModel disposed")
        dropTimer.cancel()
        moveTimer.cancel()
    }

    // TODO: Currently not called due to using JVM KMP ViewModel, (and apparently, main being the ViewModelStoreOwner, viewmodel does not clear before app exit)
    /*override fun onCleared() {
        super.onCleared()

        dropTimer.cancel()
        moveTimer.cancel()
    }*/

    private fun moveTimer() {
        moveTimer.scheduleAtFixedRate(object : TimerTask() {
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
        dropTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                randomDrop()

                if(companionService.currentDrops.value.isNotEmpty()) {
                    viewModelScope.launch {
                        // WAIT FOR ANIMATION TO END BEFORE WRITING TO THE COMPANION (AND RESETTING LIST)
                        delay((Constants.EnterAnimationDuration + Constants.ExitAnimationDuration).toLong())
                        bleScanner.writeCompanion()
                    }
                }
            }
        }, 300000, 300000) // Every 5 minutes
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