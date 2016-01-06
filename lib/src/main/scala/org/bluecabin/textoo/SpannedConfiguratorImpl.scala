package org.bluecabin.textoo

import android.text.{Spannable, SpannableString, Spanned}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private[textoo](
                                                       factory: ConfiguratorFactory,
                                                       override protected val text: Spannable,
                                                       override protected val actions: Queue[Spannable => Spannable])
  extends SpannedConfigurator with BaseConfiguratorImpl[Spanned, Spannable]
  with SpannableLinkifyImpl[Spanned, Spannable, SpannedConfigurator] {

  override protected def toResult(state: Spannable): Spanned = state

  override protected def createConfigurator(text: Spannable, actions: Queue[(Spannable) => Spannable]): SpannedConfigurator =
    new SpannedConfiguratorImpl(factory, text, actions)

}

private object SpannedConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory, text: Spanned): SpannedConfigurator = new SpannedConfiguratorImpl(factory, text match {
    case s: Spannable => s
    case _ => new SpannableString(text)
  }, Queue.empty)

}