/*§
  ===========================================================================
  OSGi-Test
  ===========================================================================
  Copyright (C) 2015 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.osgitest.hotswap.gui

import java.io.FileInputStream
import javafx.event.ActionEvent
import javafx.geometry.{Insets, Pos}
import javafx.scene.Scene
import javafx.scene.control.{Alert, Button}
import javafx.scene.layout.VBox
import javafx.stage.{FileChooser, Stage, WindowEvent}

import info.gianlucacosta.osgitest.hotswap.gui.EventHandlerConverter._
import info.gianlucacosta.osgitest.hotswap.message.MessageProvider
import org.osgi.framework.wiring.FrameworkWiring
import org.osgi.framework.{BundleContext, FrameworkUtil}


class MainStage(bundleContext: BundleContext) extends Stage {
  setScene(createScene())

  setWidth(500)
  setHeight(300)
  setTitle("OSGi test - HotSwap")
  setResizable(false)
  centerOnScreen()
  show()


  private def createScene(): Scene = {
    val buttonWidth = 200
    val buttonHeight = 32

    val mainBox = new VBox()
    mainBox.setPadding(new Insets(40))
    mainBox.setSpacing(25)
    mainBox.setAlignment(Pos.CENTER)

    val showMessageButton = new Button("Show message")
    showMessageButton.setPrefSize(buttonWidth, buttonHeight)
    showMessageButton.setOnAction((event: ActionEvent) => {
      showUserMessage()
      ()
    })

    mainBox.getChildren.add(showMessageButton)


    val updateButton = new Button("Update message bundle...")
    updateButton.setPrefSize(buttonWidth, buttonHeight)
    updateButton.setOnAction((event: ActionEvent) => {
      updateMessageBundle(bundleContext)
      ()
    })
    mainBox.getChildren.add(updateButton)

    setOnCloseRequest((event: WindowEvent) => {
      System.exit(0)
    })

    new Scene(mainBox)
  }


  def showUserMessage(): Unit = {
    val alert = new Alert(Alert.AlertType.INFORMATION)
    alert.setTitle(MainStage.this.getTitle)
    alert.setHeaderText("OSGI Test")
    alert.setContentText(MessageProvider.getMessage)
    alert.showAndWait()
  }


  def updateMessageBundle(bundleContext: BundleContext): Unit = {
    val fileChooser = new FileChooser()
    fileChooser.getExtensionFilters.add(
      new FileChooser.ExtensionFilter(
        "OSGi bundle",
        "*.jar"))

    fileChooser.setTitle("Open updated OSGi bundle...")

    val bundleFile = fileChooser.showOpenDialog(this)
    if (bundleFile != null) {
      val sourceFileStream = new FileInputStream(bundleFile)

      FrameworkUtil
        .getBundle(MessageProvider.getClass)
        .update(sourceFileStream)


      val systemBundle = bundleContext.getBundle(0)
      val frameworkWiring = systemBundle.adapt(classOf[FrameworkWiring])
      frameworkWiring.refreshBundles(null)
    }
  }
}
