package objsets

class PostReaderSuite extends munit.FunSuite with Utils {
  import scala.concurrent.duration._
  override val munitTimeout: FiniteDuration = 10.seconds

  val reader = GoogleVsApple

  test("only one apple post with 321 likes") {
    assertEquals(reader.appleposts.filter(_.likes == 321).size, 1)
  }

  test("union and filter: google and apple posts") {
    val union = reader.googleposts union reader.appleposts
    assertEquals(union.filter(p => p.likes == 321 || p.likes == 205).size, 2)
  }

  test("union, filter and descending by like") {
    val union = reader.googleposts union reader.appleposts
    val filtered = union.filter(p => p.likes == 321 || p.likes == 205)
    assert(filtered.size == 2)
    val sorted = filtered.descendingByLikes
    val (first, second) = (sorted.head, sorted.tail.head)
    assert(first.likes == 321 && second.likes == 205)
  }

  test("sorted on trending posts") {
    val (first, second) = (reader.trending.head, reader.trending.tail.head)
    assert(first.likes >= second.likes)
  }

  test("no more than 321 likes on posts") {
    assert(reader.appleposts.filter(p => p.likes > 321).size == 0)
    assert(reader.googleposts.filter(p => p.likes > 321).size == 0)
  }

  test("PostList foreach test") {
    var counter = 0;
    val expectedCount = reader.googleposts.size
    val postList = reader.googleposts.descendingByLikes
    postList.foreach(post => counter += 1)
    assertEquals(counter, expectedCount)
  }

  test("contains") {
    val nonExistingPost = randomPostSet(1).mostLiked
    val existingPost = reader.googleposts.mostLiked
    assert(reader.googleposts.contains(existingPost))
    assert(!reader.googleposts.contains(nonExistingPost))
  }

  test("last post has less likes then first in trending") {
    val first = reader.trending.head
    val last = lastPost(reader.trending)
    assert(first.likes > last.likes)
  }

  test("no negative likes count") {
    assertEquals(reader.googleposts.filter(_.likes < 0).size, 0)
    assertEquals(reader.appleposts.filter(_.likes < 0).size, 0)
  }

}
