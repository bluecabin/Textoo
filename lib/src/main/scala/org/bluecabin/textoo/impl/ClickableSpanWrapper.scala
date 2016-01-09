package org.bluecabin.textoo.impl

import android.text.style.ClickableSpan
import android.view.View

/**
  * Created by fergus on 1/8/16.
  */
private final class ClickableSpanWrapper(wrapped: ClickableSpan, handleClick: View => Boolean) extends ClickableSpan {
  override def onClick(widget: View): Unit = if (!handleClick(widget)) wrapped.onClick(widget)
}
