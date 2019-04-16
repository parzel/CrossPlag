package de.parzelsec

import javafx.application.Application
import tornadofx.*

class CrossPlag: App(InputWizard::class, Styles::class)

/**
 * The main method is needed to support the mvn jfx:run goal.
 */
fun main(args: Array<String>) {
    Application.launch(CrossPlag::class.java, *args)
}
