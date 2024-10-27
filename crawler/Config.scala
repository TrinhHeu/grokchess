import cats.syntax.all.*
import cats.effect.IO
import ciris.*
import ciris.http4s.*
import org.http4s.Uri

case class AppConfig(
     kafkaServer: String,
     downloader: DownloaderConfig
)

case class DownloaderConfig(
     url: Uri,
     totalGames: Option[Int],
     skipLines: Option[Int]
)

object Config:
def load: IO[AppConfig] =
    (kafkaServer, downloaderConfig).mapN(AppConfig.apply).load[IO]

  def downloaderConfig =
    (downloadUrl, totalGames, skipLines).mapN(DownloaderConfig.apply)

  def kafkaServer =
    env("KAFKA_SERVER").as[String].default("localhost:9094")

  def downloadUrl =
    env("DOWNLOAD_URL").as[Uri].orThrow

  def totalGames =
    env("TOTAL_GAMES").as[Int].option

  def skipLines =
    env("SKIP_LINES").as[Int].option
 
