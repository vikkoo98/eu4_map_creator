package main.dataTypes

import java.awt.Color

data class Nation(
        val tag: String,    //primary key
        var name: String = "",
        var adjective: String = "",
        var techGroup: String = "",
        var primaryCulture: String = "",
        var religion: String = "",
        var government: String = "",
        var governmentReform: String = "",
        var graphCulture: String = "",
        var governmentRank: Int = 2,
        var terrainColor: Color = Color(0, 0, 0),
        var unitColor1: Color = Color(0, 0, 0),
        var unitColor2: Color = Color(0, 0, 0),
        var unitColor3: Color = Color(0, 0, 0),
        var capital: Int = 0
) {
     fun commonString(): String {
         var output = ""
         output += "#Country name: $name\n\n"
         output += if (graphCulture != "") "graphical_culture = $graphCulture\n\n"
            else "graphical_culture = westerngfx\n\n"
         output += "color = { ${terrainColor.red} ${terrainColor.green} ${terrainColor.blue} }\n\n"
         output += "monarch_names = {\n\"Adam #0\" = 1\n\"Eve #0\" = -1\n}\n"
         output += "leader_names  = {\n Adam\n}\n"
         output += "ship_names  = {\n Noe\n}\n"
        return output
    }

    fun colorString(): String {
        var output = ""
        output += "#$name\n"
        output += "$tag = {\n"
        output += if (unitColor1 != Color(0, 0, 0)) "color1 = { ${unitColor1.red} ${unitColor1.green} ${unitColor1.blue} }\n"
            else "color1 = { 255 255 255 }\n"
        output += "color2 = { ${unitColor2.red} ${unitColor2.green} ${unitColor2.blue} }\n"
        output += "color3 = { ${unitColor3.red} ${unitColor3.green} ${unitColor3.blue} }\n"
        output += "}\n"
        return output
    }

    fun historyString(): String {
        var output = ""
        output += if (government != "") "government = $government\n" else "government = monarchy\n"
        if (governmentReform != "") output += "add_government_reform = $governmentReform\n"
        if (governmentRank != 2) output += "government_rank = $governmentRank\n"
        output += if (techGroup != "") "technology_group = $techGroup\n" else "technology_group = western\n"
        if (religion != "") output += "religion = $religion\n"
        if (primaryCulture != "") output += "primary_culture = $primaryCulture\n"
        if (capital != 0) output += "capital = $capital\n"
        return output
    }

    fun localisationString(): String {
        var output = ""
        output += " ${tag}:0 \"$name\"\n"
        output += " ${tag}_ADJ:0 \"$adjective\"\n"
        output += " ${tag}_ADJ2:0 \"$adjective\"\n"
        return output
    }
}