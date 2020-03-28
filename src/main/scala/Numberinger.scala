import java.io.File
import java.nio.file.{Files, Path}

object Numberinger {

  def main(args: Array[String]): Unit = {

    val prefix = args.head

    args.tail.foreach(arg => process(arg))

    def process(fileName: String): Unit = {
      val path = new File(fileName)
      if (path.isDirectory) {
        val files = path.listFiles()
        var i = 1
        for (file <- files) {
          val filePath = file.toPath
          val name = file.getName
          val suffix = name.substring(name.lastIndexOf("."), name.length)
          numberFile(filePath, i, suffix)
          i += 1
        }
      } else {
        val suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length)
        numberFile(path.toPath, 1, suffix)
      }
    }

    def numberFile(file: Path, number: Int, suffix: String): Unit = {
      val numberStr = if(number <= 9) s"0${number}" else number
      Files.move(file, file.resolveSibling(s"${prefix}-${numberStr}${suffix}"))
    }
  }
}
