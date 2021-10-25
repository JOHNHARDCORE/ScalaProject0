import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn.readLine
import scala.io.Source
import java.io.FileNotFoundException

object App {

	def pollForInput(query: String = ">Enter your Input"): String = {
		val input = readLine(query)
		println("\n\n")
		input
	}

	def registerUser(): User = {
		val id = pollForInput("ID: ")
		val name = pollForInput("Name: ")

		val new_user = new User(id.toInt, name)
		State.SetUser(new_user)

		return new_user
	}
	def login() {
		println("logging in")
		State.SetLoggedIn(true)
	}

	def exit() {
		println("Exiting...")
		State.SetLoggedIn(false)
		State.SetContinue(false)
	}
	def loginScreen(): Boolean = {
		println("Welcome to Scala Slots. Please log in or create a new user if you're new")
		println("[1]: Log In")
		println("[2]: Register New User")
		println("[3]: Exit")
		val input = pollForInput()
		input match {
			case "1" => { login() }
			case "2" => { registerUser() }
			case "3" => { exit() }
			case _ => println("Invalid choice")
		}

		return false
	}

	def mainMenu(): Boolean = {
		println("What would you like to do?")
		println("[1]: display my information")
		println("[2]: exit")
		val input = pollForInput()
		input match {
			case "1" => { State.GetUser().PrintInformation() }
			case "2" => { exit() }
			case _ => println("Invalid choice")			
		}

		return false
	}

	def loadFile(file: String): Map[String, String] = {
		try {
			Source.fromFile(file).
				getLines.
				map(line => (line.split("=").map(word => word.trim))).
				map({case Array(first, second) => (first, second)}).
				toMap
		} catch {
			case e: FileNotFoundException => {
				println(s"Couldn't find that file: $file\n")
				println(s"********** Place SQL login details in resource/login.txt **********")
				println(s"********** With format                                   **********")
				println(s"********** ip = your_ip:port                             **********")
				println(s"********** username = your_username                      **********")
				println(s"********** password = your_password                      **********")
				
				throw e
			}
			case e: Throwable => throw e
		}
	}
	def main(args: Array[String]) {
		println("Starting....\n\n")

		// todo: hookup database/make tables, basic game logic
		val configs = loadFile("resources/login.txt") 

		do {
			val input = State.GetLoggedIn() match {
				case true => mainMenu()
				case false => loginScreen()
			}
		} while(State.GetContinue())



		// sql driver stuff

		// val driver = "com.mysql.cj.jdbc.Driver"
		// val url = "jdbc:mysql://localhost:3306/test"
		// val username = "root"
		// val password = "p4ssword"

		// var connection:Connection = DriverManager.getConnection(url, username, password)
		// val statement = connection.createStatement()
		// val resultSet = statement.executeQuery("SELECT * FROM users;")
		// println(resultSet)
		// while ( resultSet.next() ) {
		//   println(resultSet.getString(1)+", " +resultSet.getString(2) +", " +resultSet.getString(3))
		// }
		// connection.close()
	}

}