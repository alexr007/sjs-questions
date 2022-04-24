package back

import cats.effect.IO
import cats.effect.IOApp
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import scala.util.Properties.envOrNone

object LauncherServerApp extends IOApp.Simple {

  val httpApp = Router(
    "/1" -> new Endpoints[IO].routes
  ).orNotFound

  override def run: IO[Unit] = for {
    port <- IO(envOrNone("HTTP_PORT").map(_.toInt).getOrElse(8080))
    _    <- BlazeServerBuilder[IO]
              .bindHttp(port)
              .withHttpApp(httpApp)
              .serve
              .compile
              .drain
  } yield ()
}
