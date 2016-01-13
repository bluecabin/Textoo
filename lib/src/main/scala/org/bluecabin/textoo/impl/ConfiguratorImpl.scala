package org.bluecabin.textoo.impl

import org.bluecabin.textoo.{TextooContext, Configurator}
import org.bluecabin.textoo.impl.Change.ChangeQueue

/**
  * Created by fergus on 1/6/16.
  */
private trait ConfiguratorImpl[T, C <: Configurator[T]] extends Configurator[T] {
  protected val textooContext: TextooContext

  protected final implicit def implicitTextooContext = textooContext

  protected val initState: () => T

  protected val changes: ChangeQueue[T]

  protected def updateChanges(changes: ChangeQueue[T]): C

  protected final def addChange(chg: Change[T]): C = updateChanges(chg.addTo(changes))

  protected def toResult(text: T): T = text

  override final def apply(): T = toResult(changes.foldLeft(initState())((t, change) => change(t)))
}
