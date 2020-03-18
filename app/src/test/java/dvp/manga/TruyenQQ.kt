package dvp.manga

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class TruyenQQ {
    @Test
    fun test() {
        val body = Jsoup.connect("http://truyenqq.com/truyen-con-trai.html?country=4").get().body()
        val stories = body.getElementsByClass("list-stories").first().getElementsByClass("story-item")
        print(stories.size)


    }
}