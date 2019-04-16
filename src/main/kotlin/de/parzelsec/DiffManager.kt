package de.parzelsec

import de.parzelsec.model.DocHolder
import org.apache.commons.text.similarity.LevenshteinDistance

object DiffManager {
    fun calculateSimilarity(doc1:DocHolder, doc2:DocHolder): Double {
        val in1 = doc1.contentSplit
        val in2 = doc2.contentSplit

        val text1:List<String>
        val text2:List<String>

        if(in1.size > in2.size){
            text1 = in2
            text2 = in1
        }
        else {
            text1 = in1
            text2 = in2
        }
        val uniqueness = ArrayList<Double>()
        //compare every sentence with every other sentence
        text1.forEach{ t1 ->
            var maxDif = 0.0
            text2.forEach{t2 ->
                // Get Diff
                val dif = getDiff(t1,t2)
                if(dif > maxDif){
                    maxDif = dif
                }
            }
            uniqueness.add(maxDif)
        }
        //sum up percentage
        var temp = 0.0
        uniqueness.forEach{
            temp += it
        }
        //calc effective similarity
        return temp / uniqueness.size
    }


    private fun getDiff(t1:String, t2:String): Double{
        val dif = LevenshteinDistance.getDefaultInstance().apply(t1, t2)
        return if(t2.length < t1.length){
            1.0 - dif / t1.length.toDouble()
        }
        else{
            1.0 - dif / t2.length.toDouble()
        }
    }
}
