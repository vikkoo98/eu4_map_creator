package main.dataTypes

data class Continent(
        val address: String,    //primary key
        var name: String = "",
        var provinces: MutableList<String> = mutableListOf()
)