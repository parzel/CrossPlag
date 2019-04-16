package de.parzelsec.views

import de.parzelsec.controller.FileCheckerController



class DestFolderChooseView:FolderChooseView("Additional Files Folder (Optional)", "These folders are only checked against\nthe main files not each other\n"){
    private val controller: FileCheckerController by inject()

    init{
        folderArray = controller.additionalFolder
    }
}