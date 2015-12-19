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

import javafx.event.{Event, EventHandler}

object EventHandlerConverter {
  implicit def functionToEventHandler[T <: Event](action: T => Unit): EventHandler[T] = {
    new EventHandler[T] {
      override def handle(event: T): Unit = {
        action(event)
      }
    }
  }
}