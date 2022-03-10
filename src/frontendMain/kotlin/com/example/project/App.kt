package com.example.project

import com.example.IPingService
import com.example.PingService
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.BootstrapModule
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapSelectModule
import io.kvision.BootstrapIconsModule
import io.kvision.FontAwesomeModule
import io.kvision.form.text.TextInputType
import io.kvision.form.text.textInput
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.module
import io.kvision.panel.root
import io.kvision.startApplication
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    val state = ObservableValue("!")
    override fun start() {
        root("kvapp") {
            div("Hello world") {
                textInput(TextInputType.TEXT) { }.bind(state) {
                    +it
                }
            }


            button("Run CDK") {
                onClick {
                    state.value += "!"
                    AppScope.launch {
                        console.log("Received from server - " + Model.ping("hello"))
                    }


                }
            }
        }


    }
}

object Model {

    private val pingService = PingService()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapSelectModule,
        BootstrapIconsModule,
        FontAwesomeModule,
        CoreModule
    )
}
