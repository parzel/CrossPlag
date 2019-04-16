package de.parzelsec.views

import de.parzelsec.model.FolderInput
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import tornadofx.*


abstract class FolderChooseView(viewTitle:String, description:String): View(viewTitle) {

    private val directoryChooser = DirectoryChooser()
    var folderArray = ArrayList<String>() //this will be replaced by extension
    var folderInputHolder = ArrayList<FolderInput>()
    var vBoxHolder = vbox()

    override val root = form {

        fieldset {

            label(description) {
            }

            vBoxHolder = vbox()

            button("+") {
                setOnAction {
                    addFolderChooseView(vBoxHolder)
                }
            }
        }
    }

    init {
        addFolderChooseView(vBoxHolder)
    }

    override fun onSave() {
        folderInputHolder.forEach{
            folderArray.add(it.name)
        }
    }

    private fun addFolderChooseView(vBox: VBox) {
        val folder = FolderInput()
        folderInputHolder.add(folder)
        vBox += hbox {
            useMaxWidth = true
            textfield() {
                isEditable = false
                isDisable = false
                isMouseTransparent = true
                isFocusTraversable = false
                useMaxWidth = true
                bind(folder.nameProperty())
                gridpaneConstraints {
                    columnRowIndex(0, 0)
                    hGrow = Priority.ALWAYS
                }
            }

            button("Select Folder") {
                gridpaneConstraints {
                    columnRowIndex(1, 0)
                }
                setOnAction {
                    val selectedFile = directoryChooser.showDialog(primaryStage)
                    if (selectedFile != null) {
                        folder.name = selectedFile.absolutePath
                    }
                }
            }
        }
        currentStage?.sizeToScene()
    }
}

