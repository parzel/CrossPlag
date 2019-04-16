package de.parzelsec.views

import de.parzelsec.controller.FileCheckerController

class TarFolderChooseView:FolderChooseView("Main Files Folder","All files in these folders are compared to\neach other\n"){
    private val controller: FileCheckerController by inject()

    init{
        folderArray = controller.inputFolder
    }
}