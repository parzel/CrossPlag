package de.parzelsec.views

import de.parzelsec.controller.FileCheckerController
import de.parzelsec.model.TextComparison
import tornadofx.*
import java.awt.Desktop
import java.io.IOException
import java.net.URISyntaxException




class SummaryView : View("Summary") {
    private val controller: FileCheckerController by inject()

    // use sorted list
    private var data = controller.compList.sortedWith(compareBy<TextComparison>{it.similarity}).reversed()

    override val root = tableview(data.observable()) {
        columnResizePolicy = SmartResize.POLICY

        readonlyColumn("Input File", TextComparison::doc1name).contentWidth(padding = 40.0)
        readonlyColumn("Compared File", TextComparison::doc2name).contentWidth(padding = 40.0)
        readonlyColumn("Similarity", TextComparison::percentage)
        readonlyColumn("Visualization", TextComparison::similarity).useProgressBar().remainingWidth()

        contextmenu {
            item("Open").action {
                selectedItem?.apply {
                    val file1 = doc1.file
                    val file2 = doc2.file
                    //this leads to freezing - https://stackoverflow.com/questions/23176624/javafx-freeze-on-desktop-openfile-desktop-browseuri
                    if (Desktop.isDesktopSupported()) {
                        Thread {
                            try {
                                Desktop.getDesktop().open(file1)
                                Desktop.getDesktop().open(file2)
                            } catch (e1: IOException) {
                                e1.printStackTrace()
                            } catch (e1: URISyntaxException) {
                                e1.printStackTrace()
                            }
                        }.start()
                    }
                }
            }
        }
    }
}


