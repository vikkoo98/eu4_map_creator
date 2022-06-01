package main.initTypes

import javafx.scene.control.*
import javafx.scene.layout.AnchorPane


@Suppress("UNCHECKED_CAST")
class ProvinceInit(root: AnchorPane) {
    //provinces, all tabs

    //positions tab
    val checkPositions: CheckBox = root.lookup("#check_positions") as CheckBox
    val checkDefinitions: CheckBox = root.lookup("#check_definitions") as CheckBox
    val checkDefaultMap: CheckBox = root.lookup("#check_defaultmap") as CheckBox
    val checkProvNames: CheckBox = root.lookup("#check_prov_names") as CheckBox
    val checkProvAdj: CheckBox = root.lookup("#check_prov_adj") as CheckBox
    val executePositions: Button = root.lookup("#execute_prov_placement") as Button

    //history tab
    val checkProvHistory: CheckBox = root.lookup("#check_prov_history") as CheckBox
    val executeProvHistory: Button = root.lookup("#execute_prov_history") as Button

    //areas tab
    val checkAreas: CheckBox = root.lookup("#check_areas") as CheckBox
    val checkRegions: CheckBox = root.lookup("#check_regions") as CheckBox
    val checkSuperRegions: CheckBox = root.lookup("#check_superregions") as CheckBox
    val checkContinents: CheckBox = root.lookup("#check_continents") as CheckBox
    val executeAreas: Button = root.lookup("#execute_areas") as Button

    //trade tab
    val checkTradeNode: CheckBox = root.lookup("#check_trade_nodes") as CheckBox
    val executeTrade: Button = root.lookup("#execute_trade") as Button

    //fast execute tab
    val checkPositionsAll: CheckBox = root.lookup("#check_all_positions") as CheckBox
    val checkDefinitionsAll: CheckBox = root.lookup("#check_all_definitions") as CheckBox
    val checkDefaultMapAll: CheckBox = root.lookup("#check_all_defaultmap") as CheckBox
    val checkProvNamesAll: CheckBox = root.lookup("#check_all_prov_names") as CheckBox
    val checkProvAdjAll: CheckBox = root.lookup("#check_all_prov_adj") as CheckBox
    val checkProvHistoryAll: CheckBox = root.lookup("#check_all_prov_history") as CheckBox
    val checkAreasAll: CheckBox = root.lookup("#check_all_areas") as CheckBox
    val checkRegionsAll: CheckBox = root.lookup("#check_all_regions") as CheckBox
    val checkSuperRegionsAll: CheckBox = root.lookup("#check_all_superregions") as CheckBox
    val checkContinentsAll: CheckBox = root.lookup("#check_all_continents") as CheckBox
    val executeProvAll: Button = root.lookup("#execute_prov_all") as Button

    /*
    val ID: Label = root.lookup("#anomaly_id") as Label
    val path: Label = root.lookup("#anomaly_path") as Label
    val edit: Button = root.lookup("#anomaly_edit") as Button
    val save: Button = root.lookup("#anomaly_save") as Button
    val new: Button = root.lookup("#anomaly_new") as Button
    val discard: Button = root.lookup("#anomaly_discard") as Button
    val delete: Button = root.lookup("#anomaly_delete") as Button
    val listView: ListView<String> = root.lookup("#anomaly_list") as ListView<String>
    val useAi: CheckBox = root.lookup("#anomaly_use_ai") as CheckBox
    val useAiPlayer: CheckBox = root.lookup("#anomaly_use_ai_player") as CheckBox
    val desc: Label = root.lookup("#anomaly_desc") as Label
    val pic: Label = root.lookup("#anomaly_pic") as Label
    val level: Slider = root.lookup("#anomaly_level") as Slider
    val nullSpawn: Slider = root.lookup("#anomaly_null_spawn") as Slider
    val maxOnce: CheckBox = root.lookup("#anomaly_max_once") as CheckBox
    val maxOnceGlobal: CheckBox = root.lookup("#anomaly_max_once_global") as CheckBox
    val spawnBox: TextArea = root.lookup("#anomaly_spawn_box") as TextArea
    val onSuccessBox: TextArea = root.lookup("#anomaly_on_success_box") as TextArea
    val text: TextField = root.lookup("#anomaly_text") as TextField
    val descText: TextArea = root.lookup("#anomaly_desc_text") as TextArea
    */
}

