package android.text

import junit.framework.Assert._
import junit.framework.{Assert, TestCase}

class SpannableTest extends TestCase {
  def testSpan: Unit = {
    val spannable = Spannable.Factory.getInstance().newSpannable("123456789")
    val span1 = "span1"
    spannable.setSpan(span1, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val span2 = "span2"
    spannable.setSpan(span2, 6, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    assertEquals(0, spannable.getSpanStart(span1))
    assertEquals(4, spannable.getSpanEnd(span1))
    assertEquals(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spannable.getSpanFlags(span1))
    assertEquals(6, spannable.getSpanStart(span2))
    assertEquals(8, spannable.getSpanEnd(span2))
    assertEquals(Spanned.SPAN_INCLUSIVE_INCLUSIVE, spannable.getSpanFlags(span2))
    assertSpans(Seq("span1"), spannable, 0, 4)
    assertSpans(Seq("span1"), spannable, 1, 2)
    assertSpans(Seq("span1"), spannable, 0, 5)
    assertSpans(Seq(), spannable, 4, 5)
    assertSpans(Seq("span1"), spannable, 3, 6)
    assertSpans(Seq("span1", "span2"), spannable, 3, 7)
  }

  private def assertSpans(expectedSpans: Seq[String], spannable: Spannable, start: Int, end: Int) = {
    assertEquals(expectedSpans, spannable.getSpans(start, end, classOf[String]).toSeq)
  }
}