package main.functions

import main.MainView.Companion.directoryPath
import main.dataTypes.ColorInforamtion
import main.dataTypes.CsvType
import main.dataTypes.Province
import tornadofx.Vector2D
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

 fun colorInformationParser(provincelist: List<Province>, csvPath: String, bmpPath: String, type: CsvType) {
    val inFile = File("$directoryPath\\input\\$csvPath")
    val imageFile = File("$directoryPath\\input\\$bmpPath")
    if (imageFile.exists() && inFile.exists()) {
        val image: BufferedImage = ImageIO.read(imageFile)
        //csv betöltése
        val colorList = mutableListOf<ColorInforamtion>()
        File(inFile.absolutePath).forEachLine { line->
            val splitted = line.split(',',';',ignoreCase = true)
            try {
                colorList.add(ColorInforamtion(
                        ID = splitted[0],
                        Color = Color(splitted[1].toInt(),splitted[2].toInt(),splitted[3].toInt())
                ))
            } catch (e: Exception) {}
        }
        //hozzárendeljük a provinciákhoz
        for (i: Int in provincelist.indices) {
            val colorInformation = Color(image.getRGB(provincelist[i].position.x.toInt(),provincelist[i].position.y.toInt()))
            for (j: Int in colorList.indices)
            {
                if (colorList[j].Color == colorInformation) {
                    when (type) {
                        CsvType.TAG -> provincelist[i].owner = colorList[j].ID
                        CsvType.CULTURE -> provincelist[i].culture = colorList[j].ID
                        CsvType.RELIGION -> provincelist[i].religion = colorList[j].ID
                        CsvType.TRADEGOOD -> provincelist[i].tradeGood = colorList[j].ID
                    }
                }
            }
        }
    }
}

 fun provincePlacementParser(provincelist: MutableList<Province>) {
    val inFile = File("$directoryPath\\input\\province\\placement\\provinces.csv")
    val imageSettlementsFile = File("$directoryPath\\input\\province\\placement\\settlements.bmp")
    val image2File = File("$directoryPath\\input\\province\\placement\\provinces.bmp")
    if (inFile.exists() && imageSettlementsFile.exists() && image2File.exists()) {
        val imageSettlements: BufferedImage = ImageIO.read(imageSettlementsFile)
        val image2: BufferedImage = ImageIO.read(image2File)
        //betöltjük a provinciákat a csv-ből
        File(inFile.absolutePath).forEachLine { line ->
            val splitted = line.split(',', ';', ignoreCase = true)
            try {
                provincelist.add(Province(
                        ID = splitted[0].toInt(),
                        color = Color(splitted[1].toInt(), splitted[2].toInt(), splitted[3].toInt()),
                        name = splitted[4])
                )
            } catch (e: Exception) {
            }
        }
        //betöltjük a settlementek koordinátáit, és hozzárendeljük, ezt a settlementsből és a provincesből
        for (x: Int in 0 until imageSettlements.width)
            for (y: Int in 0 until imageSettlements.height) {
                val rgbValue = Color(imageSettlements.getRGB(x, y))
                if (rgbValue == Color(255, 0, 0)
                        || rgbValue == Color(0, 0, 255)
                        || rgbValue == Color(0, 255, 0)) {
                    val provinceRGB = Color(image2.getRGB(x, y))
                    for (i: Int in provincelist.indices) {
                        if (provincelist[i].color == provinceRGB) {
                            provincelist[i].position = Vector2D(x.toDouble(), y.toDouble())
                            if (rgbValue == Color(0, 0, 255))
                                provincelist[i].climate = "water"
                            if (rgbValue == Color(0, 255, 0))
                                provincelist[i].climate = "wasteland"
                            //println("x: $x, y: $y, color: $provinceRGB")
                        }
                    }
                }
            }
    }
}