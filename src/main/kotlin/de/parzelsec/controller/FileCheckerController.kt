package de.parzelsec.controller

import de.parzelsec.DiffManager
import de.parzelsec.TextExtractor
import de.parzelsec.model.DocHolder
import de.parzelsec.model.TextComparison
import javafx.collections.FXCollections
import javafx.concurrent.Task
import tornadofx.Controller
import java.io.File
import java.util.*

class FileCheckerController : Controller() {

    val inputFolder = ArrayList<String>()
    val additionalFolder = ArrayList<String>()

    var compList = FXCollections.observableArrayList<TextComparison>()!!
    private val allowedExtensions = arrayOf("pdf", "odt", "docx", "doc", "txt")

    private var task: Task<Unit>? = null

    fun convert() {
        task = runAsync{
            val startTime = System.currentTimeMillis()

            // Read in documents from input folders
            updateMessage("Searching for documents in input folder")
            val availableFiles = ArrayList<File>()
            scanFolderForFiles(availableFiles, inputFolder)
            updateMessage("Searching for documents in additional folder")
            val additionalFiles = ArrayList<File>()
            scanFolderForFiles(additionalFiles, additionalFolder)

            //extracting text from every file
            updateMessage("Extracting text from documents")
            updateProgress(0.0, 1.0)
            val processedDocs = ArrayList<DocHolder>()
            availableFiles.forEach {
                extract(it, processedDocs)
                updateProgress(processedDocs.size / availableFiles.size.toDouble(), 1.0)
            }

            updateMessage("Extracting text from additional documents")
            updateProgress(0.0, 1.0)
            val processedDocsAdditional = ArrayList<DocHolder>()
            additionalFiles.forEach {
                extract(it, processedDocsAdditional)
                updateProgress(processedDocsAdditional.size / additionalFiles.size.toDouble(), 1.0)
            }

            // Start comparison
            updateMessage("Start comparison")
            updateProgress(0.0, 1.0)
            //calculate maximum time amount
            var max = 0
            for (i in 0 until processedDocs.size) {
                max += i
                max += processedDocsAdditional.size
            }
            //compare every file from input with input and additional
            for (index in 0 until processedDocs.size) {
                val outerDoc = processedDocs[index]
                //compare with each from the added files
                for (innerIndex in index+1 until processedDocs.size) {
                    val innerDoc = processedDocs[innerIndex]
                    updateMessage("Comparing ${outerDoc.file.name} to ${innerDoc.file.name}")
                    val similarity = DiffManager.calculateSimilarity(outerDoc, innerDoc)
                    val comp = TextComparison(processedDocs[index], processedDocs[innerIndex], similarity)
                    compList.add(comp)
                    updateProgress(compList.size / max.toDouble(), 1.0)
                }
                //compare with each from the additional files
                processedDocsAdditional.forEach {
                    updateMessage("Comparing ${outerDoc.file.name} to ${it.file.name}")
                    val similarity = DiffManager.calculateSimilarity(outerDoc, it)
                    val comp = TextComparison(processedDocs[index], it, similarity)
                    compList.add(comp)
                    updateProgress(compList.size / max.toDouble(), 1.0)
                }
            }
            println("Read all input files")

            val estimatedTime = System.currentTimeMillis() - startTime
            println("Processed all files in $estimatedTime seconds")
        }
    }

    private fun scanFolderForFiles(availableFiles: ArrayList<File>, inputFolder: ArrayList<String>) {
        inputFolder.forEach{ inputValue ->
            try {
                println(inputValue)
                File(inputValue).listFiles().forEach {
                    if(allowedExtensions.contains(it.extension)) {
                        availableFiles.add(it)
                    }
                    else{
                        println("Ignoring $it because of extension")
                    }
                }
            }catch (n:NullPointerException){
                println("Empty folder $inputValue")
            }
        }
    }

    private fun extract(it: File, processedDocs: ArrayList<DocHolder>) {
        val extractedText = TextExtractor.extractText(it)
        // Remove line breaks
        var content = extractedText.replace("\n", " ").replace("\r", " ")
        // Remove Whitespaces
        content = content.replace("\\s+".toRegex(), " ")
        // Now trim preceding and succeding whitespaces
        content = content.trim()
        // Split into sentences
        val splitContent = content.split('.','?','!')
        val holder = DocHolder(it, content, splitContent)
        if(content == ""){
            println("Skipping $it.name")
            return
        }
        println(it.name)
        println(content)
        processedDocs.add(holder)
    }

    fun stop(){
        if(task!!.isRunning){
            println("Canceling thread...")
            task!!.cancel()
        }
        else{
            println("Thread finished!")
        }
    }
}