package com.example.qrscannerpro.ui.navigation

object NavRoutes {
    const val Splash = "splash"
    const val Scanner = "scanner"
    const val Result = "result/{content}"
    const val History = "history"
    const val Generator = "generator"
    const val Settings = "settings"

    fun result(content: String): String = "result/$content"
}
