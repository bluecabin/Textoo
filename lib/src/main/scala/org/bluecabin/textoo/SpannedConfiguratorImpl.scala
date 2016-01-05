package org.bluecabin.textoo

import android.text.Spanned

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private() extends SpannedConfigurator {
  override def handleTextooLinks(): Unit = ???

  override def addLinksHandler(handler: LinksHandler): Unit = ???

  override def apply(): Spanned = ???

  override def linkifyPhoneNumbers(): SpannedConfigurator = ???

  override def linkifyEmailAddresses(): SpannedConfigurator = ???

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
  def create(factory: ConfiguratorFactory): SpannedConfigurator = new SpannedConfiguratorImpl
}