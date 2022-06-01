package main.dataTypes

import tornadofx.Vector2D
import java.awt.Color

data class Province(
        val ID: Int,
        var name: String = "",
        var adjective: String = "",
        var owner: String = "",
        var color: Color = Color(0, 0, 0),
        var culture: String = "",
        var religion: String = "",
        var tradeGood: String = "",
        var cityName: String = "",
        var tax: Int = 2,
        var production: Int = 2,
        var manpower: Int = 2,
        var climate: String = "",
        var position: Vector2D = Vector2D(0.0,0.0)
)
{
    override fun toString(): String {
        var output = ""

        if (name != "") output += "# $ID - $name\n"
        if (owner != "") {
            output += "add_core = $owner\n"
            output += "owner = $owner\n"
            output += "controller = $owner\n"
            output += "is_city = yes\n"
        }

        if (culture != "") output += "culture = $culture\n"
        if (religion != "") output += "religion = $religion\n"
        output += "hre = no\n"
        output += "base_tax = $tax\n"
        output += "base_production = $production\n"
        output += "base_manpower = $manpower\n"
        output += if (tradeGood != "") "trade_goods = $tradeGood\n" else "trade_goods = unknown\n"
        output += if (cityName != "") "capital = $cityName\n" else "capital = $ID\n"
        return output
    }
}