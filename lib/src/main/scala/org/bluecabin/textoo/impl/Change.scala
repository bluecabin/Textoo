package org.bluecabin.textoo.impl

import org.bluecabin.textoo.impl.Change.ChangeQueue

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/9/16.
  */
private trait Change[T] {
  def addTo(changes: ChangeQueue[T]): ChangeQueue[T] = changes.enqueue(this)

  def apply(text: T): T
}

private object Change {
  type ChangeQueue[T] = Queue[Change[T]]
}