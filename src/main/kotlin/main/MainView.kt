package main

import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import main.dataTypes.*
import main.functions.provincePlacementParser
import main.functions.colorInformationParser
import main.initTypes.NationInit
import main.initTypes.ProvinceInit
import tornadofx.View
import java.awt.Color
import java.time.LocalDateTime
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class MainView : View("EU4 modding tool") {

    companion object {
        var indexer = 1
        val directoryPath = "C:\\Users\\Viktor-PC\\Desktop\\mod\\testdata"
        val modTAG = "mod"
    }
    //private val controller: MainController by inject()

    override val root: AnchorPane  by fxml("/main_view.fxml")
    val directoryPath = "C:\\Users\\Viktor-PC\\Desktop\\mod\\testdata"
    val modTAG = "mod"
    //private val parser = TxtParse()

//lookupok-hoz
    private val progressBar: ProgressBar
    private val mainToolbar: ToolBar

    //map tab
    private val buttonMapTab: Button

    //provinces tab
    private val buttonProvincesTab: Button
    private val provincesTabPane: TabPane
    private val provinceInit: ProvinceInit

    //nations tab
    private val buttonNationsTab: Button
    private val nationsTabPane: TabPane
    private val nationInit: NationInit

    //others tab
    private val buttonOthersTab: Button

    init {
        val time = LocalDateTime.now()
        //indexer = time.second + time.minute * 60 + time.hour * 3600 + time.dayOfYear * 24 * 3600
        //elemek beazonosítása
        mainToolbar = root.lookup("#main_toolbar") as ToolBar
        progressBar = root.lookup("#progressbar") as ProgressBar

        buttonMapTab = root.lookup("#button_map_tab") as Button
        buttonProvincesTab = root.lookup("#button_provinces_tab") as Button
        buttonNationsTab = root.lookup("#button_nations_tab") as Button
        buttonOthersTab = root.lookup("#button_others_tab") as Button

        provincesTabPane = root.lookup("#provinces_tab_pane") as TabPane
        nationsTabPane = root.lookup("#nations_tab_pane") as TabPane

        provinceInit = ProvinceInit(root)
            nationInit = NationInit(root)

        //alapból ne látsszon egyik se
        provincesTabPane.isVisible = false
        nationsTabPane.isVisible = false

        //eu4 mappa betöltése
        mainToolbar.items[0].setOnMouseClicked { //az eu4 folder kiválasztása
            /* val directoryChooser = DirectoryChooser()
            directoryChooser.title = "Select stellaris game folder!"
            val directory = directoryChooser.showDialog(null)
            if (directory != null && File("${directory.absolutePath}\\stellaris.exe").exists()) {
                gameDirectory = directory.absolutePath
                mainToolbar.items[1].isDisable = false
                savePath()
            }*/
        }

        //provinces tabPane
        buttonProvincesTab.setOnMouseClicked {
            provincesTabPane.isVisible = true
            nationsTabPane.isVisible = false
        }
        provinceInit.executePositions.setOnMouseClicked {
            val provincelist = mutableListOf<Province>()
            provincePlacementParser(provincelist) //ez megcsinálja a beolvasását a settlements.bmp-nek, a provinces.bmp-nek, és a provinces.csv
            if (provincelist.size != 0) {
                //kiírás fájlba
                //climate.txt, egyelőre csak a wastelandek
                val targetpath = "$directoryPath\\output\\map"
                File(targetpath).mkdirs()
                //impassable = { }
                val wastelands = mutableListOf<Int>()
                for (i: Int in provincelist.indices) {
                    //it.print(provincelist[i].toString())
                    if (provincelist[i].climate == "wasteland") wastelands.add(provincelist[i].ID)
                }
                var outputWastelands = "impassable = {\n"
                var index = 0
                for (x: Int in wastelands.indices) {
                    if (index < 20) {
                        outputWastelands += "${wastelands[x]} "
                        index++
                    }
                    else {
                        outputWastelands += "\n${wastelands[x]} "
                        index = 0
                    }
                }
                outputWastelands += "\n}"
                File("$targetpath\\climate.txt").printWriter().use {
                    it.println(outputWastelands)
                }
            }
        }
        provinceInit.executeProvHistory.setOnMouseClicked {
            /*
            provincialista létrehozása
            -provinces.csv + settlements.bmp + provinces.bmp -> alap, ezért ez muszáj, megvan belőle a pozíció!
            -holder infó: defacto.bmp + tags.csv
            -kultúra: culture.bmp + cultures.csv
            -vallás: religion.bmp + religions.csv
            -development: tax.bmp + production.bmp + manpower.bmp
            -trade good: trade_good.bmp + trade_goods.csv
            */
            val provincelist = mutableListOf<Province>()
            provincePlacementParser(provincelist)
            if (provincelist.size != 0) {
                //innentől történik a lényegi dolog
                //holderek betöltése
                colorInformationParser(provincelist, "nation\\history\\tags.csv", "nation\\history\\defacto.bmp", CsvType.TAG)
                //kultúrák betöltése
                colorInformationParser(provincelist, "province\\history\\cultures.csv", "province\\history\\culture.bmp", CsvType.CULTURE)
                //vallások betöltése
                colorInformationParser(provincelist, "province\\history\\religions.csv", "province\\history\\religion.bmp", CsvType.RELIGION)
                //trade goods betöltése
                colorInformationParser(provincelist, "province\\history\\trade_goods.csv", "province\\history\\trade_good.bmp", CsvType.TRADEGOOD)
                //tax, production, manpower
                val image2File = File("$directoryPath\\\\input\\province\\history\\development.bmp")
                if (image2File.exists()) {
                    val image2: BufferedImage = ImageIO.read(image2File)
                    for (i: Int in provincelist.indices) {
                        val colorInformation = Color(image2.getRGB(provincelist[i].position.x.toInt(), provincelist[i].position.y.toInt()))
                        provincelist[i].tax = colorInformation.red
                        provincelist[i].production = colorInformation.green
                        provincelist[i].manpower = colorInformation.blue
                    }
                }

                //kiírás fájlba
                val targetpath = "$directoryPath\\output\\history\\provinces"
                File(targetpath).mkdirs()
                for (i: Int in provincelist.indices) {
                    File(targetpath + "\\${provincelist[i].ID} - ${provincelist[i].name}.txt").printWriter().use {
                        it.print(provincelist[i].toString())
                        println("ID: ${provincelist[i].ID}")
                    }
                }
            }
        }
        provinceInit.executeAreas.setOnMouseClicked {
            /*
            provincialista létrehozása
            -provinces.csv + settlements.bmp + provinces.bmp -> alap, ezért ez muszáj, megvan belőle a pozíció!
            -holder infó: defacto.bmp + tags.csv
            -kultúra: culture.bmp + cultures.csv
            -vallás: religion.bmp + religions.csv
            -development: tax.bmp + production.bmp + manpower.bmp
            -trade good: trade_good.bmp + tade_route.bmp + trade_goods.csv
            */
            val provincelist = mutableListOf<Province>()
            val areaList = mutableListOf<Area>()
            provincePlacementParser(provincelist)
            if (provincelist.size != 0) {
                //innentől történik a lényegi dolog
                //areák betöltése
                val image2File = File("$directoryPath\\input\\province\\areas\\area.bmp")
                if (image2File.exists()) {
                    val image2: BufferedImage = ImageIO.read(image2File)
                    //csv betöltése //TODO
                    var index = 1
                    var foundIt = false
                    //hozzárendeljük a provinciákhoz
                    for (i: Int in provincelist.indices) {
                        val colorInformation = Color(image2.getRGB(provincelist[i].position.x.toInt(), provincelist[i].position.y.toInt()))
                        for (j: Int in areaList.indices) {
                            //println(areaList[j].address)
                            if (areaList[j].color == colorInformation) {
                                areaList[j].provinces.add(provincelist[i].ID)
                                foundIt = true
                            }
                        }
                        if (!foundIt) {
                            //println("nem találtam")
                            areaList.add(Area(address = "area_$index", color = colorInformation, name = "area_$index")) //később módosítani kéne
                            areaList[areaList.size - 1].provinces.add(provincelist[i].ID)
                            index++
                        }
                        foundIt = false
                    }
                }


                //kiírás fájlba
                val targetpath = "$directoryPath\\output\\map"
                File(targetpath).mkdirs()
                File("$targetpath\\area.txt").printWriter().use {
                    for (i: Int in areaList.indices) {
                        it.println(areaList[i].toString())
                        //println(areaList[i].toString())
                    }
                }
            }
        }
        provinceInit.executeTrade.setOnMouseClicked {
            val provincelist = mutableListOf<Province>()
            val tradeNodeList = mutableListOf<TradeNode>()
            provincePlacementParser(provincelist)
            if (provincelist.size != 0) {
                //innentől történik a lényegi dolog
                //tradenode-ok betöltése
                val image2File = File("$directoryPath\\input\\province\\areas\\trade_region.bmp")
                val image3File = File("$directoryPath\\input\\province\\areas\\trade_region_location.bmp")
                if (image2File.exists() && image3File.exists()) {
                    var image2: BufferedImage = ImageIO.read(image2File)
                    //csv betöltése //TODO
                    var index = 1
                    var foundIt = false
                    //hozzárendeljük a provinciákat a node-okhoz, elkészítjük az esetleges extra node-okat
                    for (i: Int in provincelist.indices) {
                        val colorInformation = Color(image2.getRGB(provincelist[i].position.x.toInt(), provincelist[i].position.y.toInt()))
                        for (j: Int in tradeNodeList.indices) {
                            //println(areaList[j].address)
                            if (tradeNodeList[j].color == colorInformation) {
                                if (provincelist[i].climate != "wasteland") //ez azért kell, hogy a wasteland provinciákon is átmenjünk, de ne adjuk hozzá
                                    tradeNodeList[j].provinces.add(provincelist[i].ID)
                                if (provincelist[i].climate == "water")
                                    tradeNodeList[j].inLand = false //ha van víz a node-ban akkor nem inland node
                                foundIt = true
                            }
                        }
                        if (!foundIt) {
                            //println("nem találtam")
                            tradeNodeList.add(TradeNode(key = "trade_region_$index", color = colorInformation, name = "trade_region_$index")) //később módosítani kéne
                            tradeNodeList[tradeNodeList.size - 1].provinces.add(provincelist[i].ID)
                            index++
                        }
                        foundIt = false
                    }

                    //trade node-ok közepének a betöltése
                    image2 = ImageIO.read((image3File))
                    for (i: Int in provincelist.indices) {
                        val colorInformation = Color(image2.getRGB(provincelist[i].position.x.toInt(), provincelist[i].position.y.toInt()))
                        for (j: Int in tradeNodeList.indices) {
                            //println(areaList[j].address)
                            if (tradeNodeList[j].color == colorInformation) {
                                tradeNodeList[j].location = provincelist[i].ID
                            }
                        }
                    }
                    //trade route-ok betöltése //TODO

                    //kiírás fájlba
                    val targetPath = "$directoryPath\\output\\common\\tradenodes"
                    File(targetPath).mkdirs()
                    File("$targetPath\\00_tradenodes.txt").printWriter().use {
                        for (i: Int in tradeNodeList.indices) {
                            it.println(tradeNodeList[i])
                        }
                    }
                }
            }
        }

        //nations tabPane
        buttonNationsTab.setOnMouseClicked {
            nationsTabPane.isVisible = true
            provincesTabPane.isVisible = false
        }
        nationInit.executeNationProperties.setOnMouseClicked {
            val nationList = mutableListOf<Nation>()
            val provincelist = mutableListOf<Province>()
            provincePlacementParser(provincelist)
            val tagsFile = File("$directoryPath\\input\\nation\\history\\tags.csv")
            val propsFile = File("$directoryPath\\input\\nation\\history\\nation_properties.csv")
            val imageCapitalsFile = File("$directoryPath\\input\\nation\\history\\capital.bmp")
            if (provincelist.size != 0 && tagsFile.exists() && propsFile.exists() && imageCapitalsFile.exists())
            {
                //betöltjük a nationöket a csv-ből
                File(tagsFile.absolutePath).forEachLine { line->
                    val splitted = line.split(',',';',ignoreCase = true)
                    try {
                        nationList.add(Nation(
                                tag = splitted[0],
                                terrainColor = Color(splitted[1].toInt(),splitted[2].toInt(),splitted[3].toInt()),
                                unitColor3 = Color(splitted[1].toInt(),splitted[2].toInt(),splitted[3].toInt())
                        ))
                    } catch (e: Exception) {}
                }

                //betöltjük a nationok property-jeit a csv-ből
                File(propsFile.absolutePath).forEachLine { line->
                    val splitted = line.split(',',';',ignoreCase = true)
                    for (x: Int in nationList.indices)
                    {
                        if (nationList[x].tag == splitted[0])
                        {
                            if (splitted[1] != "") nationList[x].name = splitted[1]
                            if (splitted[2] != "") nationList[x].adjective = splitted[2]
                            if (splitted[3] != "") nationList[x].graphCulture = splitted[3]
                            if (splitted[4] != "") nationList[x].techGroup = splitted[4]
                            if (splitted[5] != "") nationList[x].primaryCulture = splitted[5]
                            if (splitted[6] != "") nationList[x].religion = splitted[6]
                            if (splitted[7] != "") nationList[x].government = splitted[7]
                            if (splitted[8] != "") nationList[x].governmentReform = splitted[8]
                            if (splitted[9] != "") nationList[x].governmentRank = splitted[9].toInt()
                            if (splitted[10] != "" && splitted[11] != "" && splitted[12] != "") nationList[x].unitColor1 = Color(splitted[10].toInt(),splitted[11].toInt(),splitted[12].toInt())
                            if (splitted[13] != "" && splitted[14] != "" && splitted[15] != "") nationList[x].unitColor2 = Color(splitted[13].toInt(),splitted[14].toInt(),splitted[15].toInt())
                            //println(nationList[x])
                        }
                    }
                }

                //megkeressük a provinciákat, és a fővárosokat hozzárendeljük a capitalokhoz
                val image2: BufferedImage = ImageIO.read(imageCapitalsFile)
                for (i: Int in provincelist.indices) {
                    val colorInformation = Color(image2.getRGB(provincelist[i].position.x.toInt(),provincelist[i].position.y.toInt()))
                    for (j: Int in nationList.indices)
                    {
                        if (nationList[j].terrainColor == colorInformation) {
                            nationList[j].capital = provincelist[i].ID
                        }
                    }
                }

                //kiírás fájlba
                //common/country_tags
                var targetpath = "$directoryPath\\output\\common\\country_tags"
                File(targetpath).mkdirs()
                File("$targetpath\\${modTAG}_countries.txt").printWriter().use {
                    for (i: Int in nationList.indices) {
                        it.println("${nationList[i].tag} = \"countries/Nation_${nationList[i].tag}.txt\"")
                    }
                }

                //common/countries
                targetpath = "$directoryPath\\output\\common\\countries"
                File(targetpath).mkdirs()
                for (i: Int in nationList.indices) {
                    File("$targetpath\\Nation_${nationList[i].tag}.txt").printWriter().use {
                        it.println(nationList[i].commonString())
                    }
                }

                //common/country_colors
                targetpath = "$directoryPath\\output\\common\\country_colors"
                File(targetpath).mkdirs()
                File("$targetpath\\${modTAG}_country_colors.txt").printWriter().use {
                    for (i: Int in nationList.indices) {
                        it.println(nationList[i].colorString())
                    }
                }

                //history/countries
                targetpath = "$directoryPath\\output\\history\\countries"
                File(targetpath).mkdirs()
                for (i: Int in nationList.indices) {
                    File("$targetpath\\${nationList[i].tag} - ${nationList[i].name}.txt").printWriter().use {
                        it.println(nationList[i].historyString())
                    }
                }

                //localisation
                targetpath = "$directoryPath\\output\\localisation"
                File(targetpath).mkdirs()
                var output = "l_english:\n"
                for (i: Int in nationList.indices) {
                    output += nationList[i].localisationString()
                }
                File("$targetpath\\countries_l_english.yml").writeText("\uFEFF" + output, Charsets.UTF_8 )
            }
        }
    }

    private fun savePath() {
        /*val file = File(JFileChooser().fileSystemView.defaultDirectory.toString() + "\\Paradox Interactive\\Stellaris\\stellaris_mod_tool.mlr")
        file.printWriter().use {
            it.println("gamedir=$gameDirectory")
            it.println("moddir=$modPath")
        }*/
    }

    private fun saveProgress() {
        /*val parser = TxtParse()
        var file: File
        if (editedAnomalies.isNotEmpty()) {
            if (!File("$modDirectory\\common\\anomalies\\").exists())
                File("$modDirectory\\common\\anomalies\\").mkdirs()
            file = File("$modDirectory\\common\\anomalies\\mod_anomalies.txt")
            file.printWriter().use {
                for (i: Int in editedAnomalies.indices) {
                    it.println(parser.addSpaces(editedAnomalies[i].toString()))
                }
            }
        }
        if (editedEvents.isNotEmpty()) {
            if (!File("$modDirectory\\events\\").exists())
                File("$modDirectory\\events\\").mkdirs()
            file = File("$modDirectory\\events\\mod_events.txt")
            file.printWriter().use {
                it.println("namespace = mod")
                for (i: Int in editedEvents.indices) {
                    it.println(parser.addSpaces(editedEvents[i].toString()))
                }
            }
        }
        if (editedAP.isNotEmpty()) {
            if (!File("$modDirectory\\common\\ascension_perks\\").exists())
                File("$modDirectory\\common\\ascension_perks\\").mkdirs()
            file = File("$modDirectory\\common\\ascension_perks\\mod_ascension_perks.txt")
            file.printWriter().use {
                for (i: Int in editedAP.indices) {
                    it.println(parser.addSpaces(editedAP[i].toString()))
                }
            }
        }
        if (!File("$modDirectory\\localisation\\english\\").exists())
            File("$modDirectory\\localisation\\english\\").mkdirs()
        file = File("$modDirectory\\localisation\\english\\mod_l_english.yml")
        var source = ""
        source += "l_english:\n"
        for (i: Int in localisations.indices)
        {
            source += "${localisations[i]}\n"
        }
        file.writeText("\uFEFF" + source, Charsets.UTF_8)

         */
    }

    private fun addLocalisation(id: String, text: String) {
        /*var savedAlready = false
        for (i: Int in localisations.indices)
        {
            if (localisations[i].id == id) {
                localisations[i] = Localisation(id,text,revision = localisations[i].revision+1)
                savedAlready = true
            }
        }
        if (!savedAlready)
        {
            localisations.add(Localisation(id,text))
        }*/
    }
}

