package back

import cats.effect.Sync
import model.Person
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder
import org.http4s.dsl.Http4sDsl

class Endpoints[F[_]: Sync] extends Http4sDsl[F] with CirceEntityEncoder {

  def routes = HttpRoutes.of[F] {
    case GET -> Root / "hello" / name => Created(s"Hello: $name")
    case GET -> Root / "bye" / name   => Ok(s"Bye: $name")
    case GET -> Root / "people"       =>
      Ok(
        List(
          Person(1, "Jim"),
          Person(2, "Bill"),
          Person(3, "Alex")
        )
      )
  }

}
