package main.initTypes

import javafx.scene.control.*
import javafx.scene.layout.AnchorPane


@Suppress("UNCHECKED_CAST")
class NationInit(root: AnchorPane) {
    //nation properties tab
    val checkCommonCountries: CheckBox = root.lookup("#check_common_countries") as CheckBox
    val checkCountryColors: CheckBox = root.lookup("#check_country_colors") as CheckBox
    val checkCountries: CheckBox = root.lookup("#check_mod_countries") as CheckBox
    val checkCountryLocalisation: CheckBox = root.lookup("#check_country_localisation") as CheckBox
    val checkHistoryCountries: CheckBox = root.lookup("#check_history_countries") as CheckBox
    val executeNationProperties: Button = root.lookup("#execute_nation_properties") as Button

    //full nation tag
    /*val ID: Label = root.lookup("#anomaly_id") as Label
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

