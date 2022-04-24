package front

import cats.effect.IO
import cats.effect.IOApp
import fs2.Stream
import io.circe.syntax.EncoderOps
import model.Person
import org.http4s._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits.http4sLiteralsSyntax

object LauncherWebApp extends IOApp.Simple {
  def action(x: Any): IO[Unit] = IO(println(x))
  val pre: IO[Unit] = action("starting...")
  val post: IO[Unit] = action("Done!")
  // fs2 works well
  val people: IO[Unit] = Stream
    .emits(List(1, 2, 3))
    .map(x => Person(x, "Jim").asJson.noSpaces)
    .covary[IO]
    .evalTap(action)
    .compile
    .drain

  val req: Request[IO] = Request[IO](Method.GET, uri"http://localhost:8080/1/people")

  val http: IO[Unit] =
    IO.unit
  // doesn't work
//    EmberClientBuilder
//      .default[IO]
//      .build
//      .use(_.expect[String](req))
//      .flatMap(action)

  /** it is just browser console output
    * to explore Typelevel stack cats / effects / fs / http4s
    * with scalaJS
    */
  override def run: IO[Unit] = pre >> http >> people >> post
}
