package org.bluecabin.textoo.impl

import org.bluecabin.textoo.Configurator
import org.bluecabin.textoo.impl.Change.ChangeQueue

/**
  * Created by fergus on 1/6/16.
  */
private trait ConfiguratorImpl[T, C <: Configurator[T]] extends Configurator[T] {
  protected val initState: () => T

  protected val changes: ChangeQueue[T]

  protected def copy(changes: ChangeQueue[T]): C

  protected final def addChange(chg: Change[T]): C = copy(chg.addTo(changes))

  protected def toResult(text: T): T

  override final def apply(): T = toResult(changes.foldLeft(initState())((t, change) => change(t)))
}
