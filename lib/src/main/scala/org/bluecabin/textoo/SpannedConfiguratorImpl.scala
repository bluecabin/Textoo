package org.bluecabin.textoo

import android.text.{Spannable, SpannableString, Spanned}

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private(text: Spanned) extends SpannedConfigurator {
  override def handleTextooLinks(): Unit = ???

  override def addLinksHandler(handler: LinksHandler): Unit = ???

  override def apply(): Spanned = {
    // TODO: implement
    new SpannableString("test")
  }

  override def linkifyPhoneNumbers(): SpannedConfigurator = ???

  override def linkifyEmailAddresses(): SpannedConfigurator = {
    // TODO: impement
    this
  }

  override def linkifyAll(): SpannedConfigurator = ???

  override def linkifyMapAddresses(): SpannedConfigurator = ???

  override def linkifyWebUrls(): SpannedConfigurator = ???
}

private object SpannedConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory, text: Spanned): SpannedConfigurator = new SpannedConfiguratorImpl(text match {
    case s: Spannable => s
    case _ => new SpannableString(text)
  })
}