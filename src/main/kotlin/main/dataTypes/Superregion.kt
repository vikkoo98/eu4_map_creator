package main.dataTypes

data class Superregion(
        val address: String,    //primary key
        var name: String = "",
        var regions: MutableList<String> = mutableListOf()
)

/*
{
    override fun toString(): String {
        var output = ""
        output += "name = $name\n"
        output += "path = $path\n"
        output += "remote_file_id = $remote_file_id\n"
        output += "supported_version = $supported_version\n"
        output += "tags = {\n"
        for (i: Int in tags.indices) {
            output += "\t${tags[i]}\n"
        }
        output += "}"
        return output
    }
}*/