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

package info.gianlucacosta.osgitest.hotswap.boot

import javafx.application.Application
import javafx.stage.{Stage, StageStyle}


object App {
  def main(args: Array[String]) {
    Application.launch(classOf[App], args: _*)
  }
}

class App extends Application {

  private val startupBundleNames = List(
    "hotswap-gui-1.0.jar",
    "hotswap-message-1.0.jar",
    "scala-library-2.11.7.jar"
  )

  override def start(primaryStage: Stage) {
    //It seems that the primary stage *must* be shown, even if totally
    // invisible, to prevent bugs when hiding secondary windows
    primaryStage.initStyle(StageStyle.UTILITY)
    primaryStage.setOpacity(0)
    primaryStage.show()

    new Thread(new Runnable {
      override def run(): Unit = {
        OsgiService.startOSGi(startupBundleNames)
      }
    }).start()
  }
}
