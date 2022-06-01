package main


import javafx.application.Application
import javafx.fxml.FXMLLoader
import main.styles.Styles
import tornadofx.App

class MyApp : App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)

}
