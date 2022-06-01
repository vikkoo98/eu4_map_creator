package main.functions

import java.io.File

class TxtParse {

    fun parseFile(path: String): List<String> {
        /*
        Először betöltjük a file-t
        elemenként szétszedjük a file-t stringekre
        ezek a stringek nyersen tartalmazzák ami az egyes konstruktorok majd szétszednek ahogy nekik kell
        ezeket visszaadjuk Listaként
         */

        val result: MutableList<String> = mutableListOf() //deklaráljuk ami majd a kimenet lesz
        var layer: Int
        var TMP: String
        var locVariables: MutableList<String>
        File(path).walk().forEach { file ->
            if (file.absolutePath.contains(".txt")) {
                layer = 0
                TMP = ""
                locVariables = mutableListOf()
                File(file.absolutePath).forEachLine {
                    var line: String = if (it.contains('#')) {
                        it.substring(0, it.indexOf('#'))
                    } else {
                        it
                    }

                    if (line.contains('@') && line.contains('='))
                    {
                        if (line.indexOf('@') < line.indexOf('='))
                            locVariables.add(line.replace(" ",""))
                        else
                        {
                            for (i: Int in locVariables.indices)
                            {
                                if (line.contains(locVariables[i].substring(0,locVariables[i].indexOf('=')-1))) {
                                    line = line.substring(0,line.indexOf('@')) + locVariables[i].substring(locVariables[i].indexOf('=')+1)
                                }

                            }
                        }
                    }

                    if (layer == 0) //egyes elemeket észleljük
                    {
                        if (line.contains('=')) //van a sorban új elem elkezdve
                        {
                            TMP = "file_path = ${file.name}\n"
                            TMP += "entity_id = ${line.substring(0, line.indexOf('=') - 1)}\n"
                        }
                    }

                    if (line.contains('}')) {
                        for (i: Int in line.indices)
                            if (line[i] == '}') layer--
                    }

                    if (layer > 0) {
                        TMP += "${line}\n"
                    }
                    if (line.contains('{')) {
                        for (i: Int in line.indices)
                            if (line[i] == '{') layer++
                    }

                    if (line.contains('}') && layer == 0) {
                        TMP = TMP.replace("\t", "")
                        TMP = TMP.replace(" ", "")
                        TMP = TMP.replace("\"","")
                        result.add(TMP)
                    }
                }
            }
        }
        for (i: Int in result.indices) {
            result[i] = result[i].replace("}","\n}").replace("{","{\n").replace("\n\n","\n")
        }
        return result
    }

    fun parseLoc(path: String, value: String): String {
        /*
        megnézzzük a mappa fájljait
        megkeressük benne a jó sort
        visszaadjuk a "részt"
         */
        var output = ""

        File(path).walk().forEach { file ->
            if (file.absolutePath.contains(".yml")) {
                File(file.absolutePath).forEachLine {
                    if (it.contains(" $value:"))
                    {
                        output = it.substring(it.indexOf("\"")+1,it.lastIndexOf("\""))
                    }
                }
            }
        }


        return output
    }

    fun addSpaces(input: String): String {
        val splittedTMP = input.split('\n').toMutableList()
        var TMP = ""
        var level = 0
        for (i: Int in splittedTMP.indices)
        {
            if (splittedTMP[i].contains('}'))
                level--
            var spaces = ""
            if (level>0) for (j: Int in 0 until level)
            {
                spaces += "\t"
            }
            if (splittedTMP[i].contains('{'))
                level++


            TMP += if (i < splittedTMP.size-1) {
                spaces + splittedTMP[i] + "\n"
            } else {
                spaces + splittedTMP[i]
            }
        }
        return TMP
    }
}