package main.dataTypes

data class Region(
        val address: String,    //primary key
        var name: String = "",
        var areas: MutableList<String> = mutableListOf()
)