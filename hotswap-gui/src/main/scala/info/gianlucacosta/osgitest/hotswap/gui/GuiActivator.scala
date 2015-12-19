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

import javafx.application.Platform

import info.gianlucacosta.osgitest.hotswap.gui.RunnableConverter._
import org.osgi.framework.{BundleActivator, BundleContext}

class GuiActivator extends BundleActivator {
  private var mainStage: MainStage = _

  override def start(context: BundleContext) {
    System.out.println(s"GuiActivator.start() ==> Activator id: ${System.identityHashCode(this)}")

    printBundles(context)

    Platform.runLater(() =>
      mainStage = new MainStage(context)
    )
  }


  override def stop(context: BundleContext) {
    System.out.println(s"GuiActivator.stop() ==> Activator id: ${System.identityHashCode(this)}")

    Platform.runLater(() =>
      mainStage.hide()
    )
  }


  private def printBundles(context: BundleContext) {
    println("-------------")

    context.getBundles.foreach(bundle =>
      println(s"${bundle.getBundleId} - ${bundle.getSymbolicName} (${bundle.getVersion})")
    )

    println("-------------")
  }
}
