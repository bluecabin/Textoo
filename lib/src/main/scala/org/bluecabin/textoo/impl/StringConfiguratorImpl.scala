package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.Html.{ImageGetter, TagHandler}
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import android.text.{Html, Spannable}
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.util.CharSequenceSupport
import org.bluecabin.textoo.{SpannedConfigurator, StringConfigurator, TextooContext}
import CharSequenceSupport._
import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class StringConfiguratorImpl private(override protected val initState: () => String,
                                             override protected val changes: ChangeQueue[String] = Queue.empty)
                                            (implicit override protected val textooContext: TextooContext)
  extends StringConfigurator(textooContext)
  with ConfiguratorImpl[String, StringConfiguratorImpl] {

  override def parseHtml(): SpannedConfigurator =
    new SpannedConfiguratorImpl({ () =>
      Html.fromHtml(apply()).toSpannable
    })

  override def parseHtml(imageGetter: ImageGetter, tagHandler: TagHandler): SpannedConfigurator =
    new SpannedConfiguratorImpl({ () =>
      Html.fromHtml(apply(), imageGetter, tagHandler).toSpannable
    })

  private def toSpannedConfigurator = new SpannedConfiguratorImpl({ () => apply().toSpanned })

  override def linkifyEmailAddresses(): SpannedConfigurator = toSpannedConfigurator.linkifyEmailAddresses()

  override def linkifyPhoneNumbers(): SpannedConfigurator = toSpannedConfigurator.linkifyPhoneNumbers()

  override def linkify(pattern: Pattern, scheme: String): SpannedConfigurator =
    toSpannedConfigurator.linkify(pattern, scheme)

  override def linkify(p: Pattern, scheme: String, matchFilter: MatchFilter,
                       transformFilter: TransformFilter): SpannedConfigurator =
    toSpannedConfigurator.linkify(p, scheme, matchFilter, transformFilter)

  override def linkifyAll(): SpannedConfigurator = toSpannedConfigurator.linkifyAll()

  override def linkifyMapAddresses(): SpannedConfigurator = toSpannedConfigurator.linkifyMapAddresses()

  override def linkifyWebUrls(): SpannedConfigurator = toSpannedConfigurator.linkifyWebUrls()

  override protected def updateChanges(newChanges: ChangeQueue[String]): StringConfiguratorImpl =
    new StringConfiguratorImpl(initState, newChanges)
}

private object StringConfiguratorImpl {
  def create(context: TextooContext, text: String): StringConfigurator =
    new StringConfiguratorImpl(() => text)(context)
}