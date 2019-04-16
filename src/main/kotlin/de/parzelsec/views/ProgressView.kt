package de.parzelsec.views

import tornadofx.*


class ProgressView: View("Progress") {

    private val status: TaskStatus by inject()


    override val root = form {

        prefWidth = 400.0
        prefHeight = 80.0

        fieldset {
            paddingAll = 8.0

            progressbar(status.progress) {
                useMaxWidth = true
            }

            label(status.message) {
                println(status.message)
            }

            status.completed.onChange {
                close()
            }
        }
    }
}

