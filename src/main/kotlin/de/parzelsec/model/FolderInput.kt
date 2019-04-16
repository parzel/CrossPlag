package de.parzelsec.model

import tornadofx.getProperty
import tornadofx.property

class FolderInput {
    var name: String by property<String>()
    fun nameProperty() = getProperty(FolderInput::name)
}