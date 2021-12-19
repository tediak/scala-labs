package objsets

import scala.util.Random

trait Utils {
  val alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  def randStr(n:Int) = (1 to n).map(_ => alpha(Random.nextInt(alpha.length))).mkString

  def randomPostSet(count: Int): PostSet = {
    val r = new Random(1234)
    (1 to count).toList.foldLeft[PostSet](new Empty) { (acc, id) =>
      acc.incl(new Post(randStr(8), randStr(100), r.nextInt(1000)))
    }
  }

  def lastPost(postList: PostList): Post = {
    if (postList.isEmpty) throw new Error("Empty post list")
    else if (postList.tail.isEmpty) postList.head
    else lastPost(postList.tail)
  }
}