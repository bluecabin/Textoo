package org.bluecabin.textoo

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/6/16.
  */
private trait BaseConfiguratorImpl[R, S] extends BaseConfigurator[R] {
  protected def text: S

  protected def actions: Queue[S => S]

  protected def toResult(state: S): R

  final override def apply(): R = {
    def runActions(pending: Queue[S => S], result: S): S = {
      pending.headOption match {
        case Some(action) => runActions(pending.tail, action(result))
        case None => result
      }
    }
    toResult(runActions(actions, text))
  }
}
