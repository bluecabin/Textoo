package org.bluecabin.textoo.impl

import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.{TextooContext, BaseConfigurator}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/6/16.
  */
private trait BaseConfiguratorImpl[T, O, C <: BaseConfigurator[O]] {
  self: BaseConfigurator[O] =>

  protected val initState: () => T

  protected val changes: ChangeQueue[T]

  protected def copy(changes: ChangeQueue[T]): C

  protected final def addChange(chg: Change[T]): C = copy(chg.addTo(changes))

  protected def toResult(text: T): O

  override final def apply(): O = toResult(changes.foldLeft(initState())((t, change) => change(t)))
}
