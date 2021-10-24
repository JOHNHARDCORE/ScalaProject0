import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn.readLine

object App {

	def pollForInput(query: String = ">Enter your Input"): String = {
		readLine(query + "\n")
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
			case "2" => { println("implement this")}
			case "3" => { exit() }
			case _ => println("Invalid choice")
		}

		return false
	}

	def mainMenu(): Boolean = {
		println("What would you like to do?")
		println("[1]: test")
		println("[2]: exit")
		val input = pollForInput()
		input match {
			case "1" => { println("u chose this life") }
			case "2" => { exit() }
			case _ => println("Invalid choice")			
		}

		return false
	}

	def main(args: Array[String]) {
		println("Starting....\n\n")

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