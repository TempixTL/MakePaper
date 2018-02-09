import scala.io.StdIn._
import scala.io.Source
import java.io.PrintWriter

object MakePaper {
	val ver = 1.0

	//Edit this line if you are supplying a custom base file with a different name
	val baseFile = "base.tex"

	//Feel free to edit the default values (or leave them empty to ignore)
	val defaultName = "Thomas Lauerman"
	val defaultProf = ""
	val defaultCourse = ""
	val defaultDate = "\\today" //Current date in LaTeX

	def main(args: Array[String]): Unit = {
		println(s"Tempix Paper Template Generator v$ver")

		//Reading values
		val fileName = if (args.length == 0) getParameter("Filename") else args(0)
		val elements = 	Map("{MP_AUTHOR}" -> getParameter("Name", defaultName),
												"{MP_PROF}" 	-> getParameter("Professor", defaultProf),
												"{MP_COURSE}" -> getParameter("Course", defaultCourse),
												"{MP_DATE}" 	-> getParameter("Date", defaultDate),
												"{MP_TITLE}"  -> getParameter("Paper Title"))

		// Reading/writing to file
		val pw = new PrintWriter(fileName)
		val base = Source.fromFile(baseFile)
		base.getLines.foreach(line => pw.println(replaceAllOccurences(line, elements.toArray)))
		base.close
		pw.close

		println(s"\nFile created: $fileName")
	}

	def getParameter(par: String, default: String = ""): String = {
		if (default.isEmpty) {
			print(s"$par: ")
			readLine
		} else {
			print(s"$par ($default): ")
			val ret = readLine
			if (ret.isEmpty) default else ret
		}
	}

	def replaceAllOccurences(str: String, replace: Array[(String, String)]): String = {
		def recReplace(newStr: String, i: Int): String = {
			if (i >= replace.length) newStr
			else {
				val (k, v) = replace(i)
				recReplace(newStr.replace(k, v), i+1)
			}
		}
		recReplace(str, 0)
	}
}
