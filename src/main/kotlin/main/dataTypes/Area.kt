package main.dataTypes

import java.awt.Color

data class Area(
        val address: String,    //primary key
        var name: String = "",
        val color: Color = Color(0,0,0),
        var provinces: MutableList<Int> = mutableListOf()
) {
    override fun toString(): String {
        var output = ""

        output += " $address = {\n"
        var index = 0
        for (x: Int in provinces.indices) {
            if (index < 20) {
                output += "${provinces[x]} "
                index++
            }
            else {
                output += "\n${provinces[x]} "
                index = 0
            }
        }
        output += "}\n"
        return output
    }
}