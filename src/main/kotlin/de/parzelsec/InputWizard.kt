package de.parzelsec

import de.parzelsec.controller.FileCheckerController
import de.parzelsec.views.DestFolderChooseView
import de.parzelsec.views.ProgressView
import de.parzelsec.views.SummaryView
import de.parzelsec.views.TarFolderChooseView
import tornadofx.*

class InputWizard : Wizard("Choose Folders with Files", "Choose folders that contain your files you want to check against eacht other") {
    private val controller: FileCheckerController by inject()

    init {
        add(TarFolderChooseView::class)
        add(DestFolderChooseView::class)
        finishButtonTextProperty.value = "Compare"
    }

    override fun onSave() {
        controller.convert()
        //this call is blocking
        find<ProgressView>().openModal(block=true)
        //this only cancels it if the thread is not finished
        controller.stop()
        replaceWith(SummaryView::class)
    }

    override fun onDock() {
        primaryStage.width = 640.0
        primaryStage.height = 500.0
        primaryStage.centerOnScreen()
    }
}