package de.parzelsec.model

class TextComparison(val doc1:DocHolder, val doc2:DocHolder, val similarity: Double){
    val doc1name = doc1.file.name!!
    val doc2name = doc2.file.name!!
    val percentage = "%.2f".format((similarity*100))
}
