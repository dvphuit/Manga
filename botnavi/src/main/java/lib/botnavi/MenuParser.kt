package lib.botnavi

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.XmlResourceParser
import android.util.AttributeSet
import android.util.Xml
import androidx.annotation.MenuRes
import lib.botnavi.botnavi.R
import org.xmlpull.v1.XmlPullParser

/**
 * @author dvphu on 24,March,2020
 */

class MenuParser(private val context: Context, @MenuRes private val menuRes: Int) {

    val menus = mutableListOf<Menu>()

    init {
        parse()
    }

    private fun parse() {
        @SuppressLint("ResourceType")
        val parser = context.resources.getLayout(menuRes)
        val attrs = Xml.asAttributeSet(parser)
        parseMenu(parser, attrs)
    }

    private fun skipMenuTagStart(parser: XmlResourceParser) {
        var currentEvent = parser.eventType
        do {
            if (currentEvent == XmlPullParser.START_TAG) {
                val name = parser.name
                require(name == "menu") { "Expecting menu, got $name" }
                break
            }
            currentEvent = parser.next()
        } while (currentEvent != XmlPullParser.END_DOCUMENT)
    }

    private fun parseMenu(parser: XmlResourceParser, attrs: AttributeSet) {
        skipMenuTagStart(parser)
        var eventType = parser.eventType
        var isEndOfMenu = false
        while (!isEndOfMenu) {
            val name = parser.name
            when {
                eventType == XmlPullParser.START_TAG && name == "item" -> {
                    parseMenuItem(attrs)
                }
                eventType == XmlPullParser.END_TAG && name == "menu" -> isEndOfMenu = true
                eventType == XmlPullParser.END_DOCUMENT -> throw RuntimeException("Unexpected end of document")
            }
            eventType = parser.next()
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun parseMenuItem(attrs: AttributeSet) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.BotNavItem)
        val id = attr.getResourceId(R.styleable.BotNavItem_android_id, -1)
        val title = attr.getText(R.styleable.BotNavItem_android_title)
        val icon = attr.getResourceId(R.styleable.BotNavItem_android_icon, 0)
        val color = attr.getColor(R.styleable.BotNavItem_color, 0)
        menus.add(Menu(id, title, icon, color))
        attr.recycle()
    }
}

data class Menu(val id: Int, val title: CharSequence, val icon: Int, val color: Int)