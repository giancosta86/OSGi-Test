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

import java.util.ServiceLoader

import org.osgi.framework.Constants
import org.osgi.framework.launch.FrameworkFactory

import scala.collection.JavaConversions._

object OsgiService {
  def startOSGi(bundleNames: Seq[String]) {
    val frameworkFactory = ServiceLoader
      .load(classOf[FrameworkFactory])
      .iterator()
      .next()

    val configurationMap = Map(
      Constants.FRAMEWORK_STORAGE_CLEAN ->
        Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT,

      "org.osgi.framework.system.packages.extra" ->
        List(
          "javafx.application",
          "javafx.collections",
          "javafx.event",
          "javafx.geometry",
          "javafx.scene",
          "javafx.scene.control",
          "javafx.scene.layout",
          "javafx.stage").mkString(",")
    )

    val framework = frameworkFactory.newFramework(configurationMap)
    framework.start()


    val bundleContext = framework.getBundleContext

    bundleNames.foreach(bundleName => {
      val bundlePath = s"/info/gianlucacosta/osgitest/bundles/${bundleName}"

      val bundleUrl = getClass.getResource(bundlePath).toString

      bundleContext.installBundle(
        bundleUrl
      )
    })


    bundleContext.getBundles.foreach(_.start())

    framework.waitForStop(0)
  }
}
