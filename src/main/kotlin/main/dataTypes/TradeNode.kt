package main.dataTypes

import tornadofx.Vector2D
import java.awt.Color

data class TradeNode (
        val key: String,    //primary key
        var name: String = "",
        var location: Int = 0,
        val color: Color = Color(0,0,0),
        var aiPropagate: Boolean = false,
        var provinces: MutableList<Int> = mutableListOf(),
        var inLand: Boolean = true,
        var connections: MutableList<Connection> = mutableListOf()
) {
    override fun toString(): String {
        var output = ""
        output += "$key = {\n"
        output += "location = $location\n"
        if (inLand) output += "inland = yes\n"
        for (i: Int in connections.indices) {
            output += "outgoing = {\n"
            output += "${connections[i]}\n"
            output += "}\n"
        }
        output += "members = {\n"
        for (i: Int in provinces.indices) output += "${provinces[i]} "
        output += "\n}\n"
        if (aiPropagate) output += "ai_will_propagate_through_trade = yes\n"
        output += "}"
        return output
    }
}

data class Connection (
        val key: String,
        var provinces: MutableList<Int> = mutableListOf(),
        var points: MutableList<Vector2D> = mutableListOf()
) {
    override fun toString(): String {
        var output = ""
        output += "name = \"$key\""
        output += "path = {\n"
        for (i: Int in provinces.indices) output += "${provinces[i]} "
        output += "\n}\n"
        output += "control = {\n"
        for (i: Int in points.indices) output += "${points[i].x} ${points[i].y} "
        output += "\n}"
        return output
    }
}

/*
malacca={
	location=1361
	outgoing={
		name="comorin_cape"
		path={
			1348 1349 1613 1614 1340
			# Straits of Malacca -> Nicobar Islands ->
			# Southern Bay of Bengal -> Ceylon Sea ->
			# Comorin Cape
		}
		control={
			4242.000 787.000 4092.000 727.000
			# Nicobar Islands -> Ceylon Sea (down 25px)
		}
	}
	outgoing={
		name="ganges_delta"
		path={
			1348 1347 1345 1344 1343
		}
		control={
			4329.000000 740.000000 4311.000000 813.000000 4279.000000 878.000000 4242.000000 943.000000
		}
	}
	members={
		593 594 595 596 597 598 599 617 618 619 620 621 622 623 636 637 638 639 640 659 1361 1998 1999 2390 2391 2392 2393 2394 2673 2674 2675 2676 2677 2678 2679 2680 2681 2682 2683 2684 2685 2686 2703 2704 2705 2706 2707 2708 2709 2710 2711 2712 2740 1101
	}

	ai_will_propagate_through_trade = yes
}
*/