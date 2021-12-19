package objsets

class PostSetSuite extends munit.FunSuite with Utils {

  val set1 = new Empty()

  val a = new Post("a", "a body", 20)
  val b = new Post("b", "b body", 20)

  val set2: PostSet = set1.incl(a)
  val set3: PostSet = set2.incl(b)

  val c = new Post("c", "c body", 7)
  val d = new Post("d", "d body", 9)

  val set4c: PostSet = set3.incl(c)
  val set4d: PostSet = set3.incl(d)
  val set5: PostSet = set4c.incl(d)

  def asSet(posts: PostSet): Set[Post] = {
    var res = Set[Post]()
    posts.foreach(res += _)
    res
  }

  def size(set: PostSet): Int = asSet(set).size

  test("filter: on empty set") {
    assertEquals(size(set1.filter(tw => tw.user == "a")), 0)
  }

  test("filter: a on set5") {
      assertEquals(size(set5.filter(tw => tw.user == "a")), 1)
  }

  test("filter: twenty on set5") {
      assertEquals(size(set5.filter(tw => tw.likes == 20)), 2)
  }

  test("filter: large set") {
    val large = randomPostSet(350)
    val predicate = (post: Post) => post.likes > 100
    // Will use built-in filter function for Set to check my implementation
    val filteredCorrectly = asSet(large).filter(predicate)
    assertEquals(asSet(large.filter(predicate)), filteredCorrectly)
  }

  test("filterAcc: empty set") {
    assertEquals(asSet(set1.filterAcc(p => true, set3)), asSet(set3))
  }

  test("filterAcc: non empty set") {
    assertEquals(asSet(set3.filterAcc(p => p.likes != 20, set2)), asSet(set2))
  }

  test("union: set4c and set4d") {
      assertEquals(size(set4c.union(set4d)), 4)
  }

  test("union: with empty set1") {
      assertEquals(size(set5.union(set1)), 4)
  }

  test("union: with empty set2") {
      assertEquals(size(set1.union(set5)), 4)
  }

  test("union: don't have duplicates") {
    assertEquals(asSet(set2 union set3), asSet(set3))
  }

  test("union: with large sets") {
    val size1 = 250
    val size2 = 300
    val large1 = randomPostSet(size1)
    val large2 = randomPostSet(size2)
    assertEquals(size(large1 union large2), size1 + size2)
  }

  test("descending: set5") {
    val trends = set5.descendingByLikes
    assert(!trends.isEmpty)
    assert(trends.head.user == "a" || trends.head.user == "b")
  }

  test("most liked when union empty and nonempty sets") {
    assertEquals(set1.union(set3).mostLiked.likes, 20)
  }

  test("remove: on empty set") {
    assertEquals(asSet(set1.remove(c)), asSet(set1))
  }

  test("remove: when trying to delete existing element") {
    assertEquals(asSet(set3.remove(b)), asSet(set2))
  }

  test("remove: when trying to delete not existing element") {
    assertEquals(asSet(set3.remove(c)), asSet(set3))
  }

  test("mostLiked: non empty set") {
    assertEquals(set3.mostLiked.likes, 20)
  }

  test("mostLiked: empty set") {
    intercept[Error](set1.mostLiked)
  }

  test("mostLiked: union of sets") {
    assertEquals(
      (set1 union set3).mostLiked,
      set3.mostLiked
    )
  }

  test("incl: non empty set") {
    assertEquals(asSet(set3.incl(c)), asSet(set3 union set4c))
  }

  test("incl: empty set") {
    assertEquals(
      asSet(set1.incl(a)),
      asSet(set2)
    )
  }

  test("incl: check for duplicates") {
    val existingPost = asSet(set4d).head
    assertEquals(
      asSet(set4d.incl(existingPost)),
      asSet(set4d)
    )
  }

  test("foreach") {
    var counter = 0;
    val expectedCounterValue = scala.util.Random.nextInt(20)
    val set = randomPostSet(expectedCounterValue)
    set.foreach(post => counter += 1)
    assertEquals(counter, expectedCounterValue)
  }

  import scala.concurrent.duration._
  override val munitTimeout: FiniteDuration = 10.seconds

}
