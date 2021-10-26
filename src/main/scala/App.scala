import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn.readLine
import scala.io.Source
import java.io.FileNotFoundException

object App {
	def pollForInput(query: String = ">Enter your Input"): String = {
		val input = readLine(s"$query\n")
		println("\n")
		input
	}

	def findUser() {
		val name = pollForInput("Name: ")

		Database.GetUser(name) match {
			case Some(res) => {
				res.PrintInformation()
			}
			case None => {
				println(s"Couldn't find user ${name}")
			}
		}
	}

	def registerUser(): Option[User] = {
		// could conslidate these two
		val name = pollForInput("Name: ")
		val pass = pollForInput("Password: ")

		var new_user = new User(name, pass)
		try {
			Database.GetUser(name) match {
				case Some(res) => { println("User already exists!\n"); None }
				case None => {
					Database.InsertNewUser(new_user)
					State.SetUser(new_user)
					Some(new_user)
				}
			}
		} catch {
			case e: SQLException => { println("Not saving this session."); State.SetSave(false); None }
		}
	}

	def login() {
		val name = pollForInput("Name: ")
		val pass = pollForInput("Password: ")

		Database.GetUser(name) match {
			case Some(res) => { 
				if (!(res.GetPassword() == pass && res.GetUsername() == name)) { return }

				State.SetLoggedIn(true)
				State.SetUser(res)
			}
			case None => {
				println(s"Incorrect login")
			}
		}
	}

	def exit() {
		println("Exiting...")
		val user = State.GetUser()
		if(State.GetSave() && user.GetID() != -1) {
			Database.SaveUser(user)
		}
		State.SetLoggedIn(false)
		State.SetContinue(false)
	}

	def connectToDB() = {
		val configs = loadFile("resources/login.txt") 

		val driver = "com.mysql.cj.jdbc.Driver"
		val url = s"jdbc:mysql://${configs("ip")}/${configs("db")}"
		val username = s"${configs("username")}"
		val password = s"${configs("password")}"

		Class.forName(driver)
		Database.SetConnection(url, username, password)
	}

	def loginScreen(): Boolean = {
		println("Welcome to Scala Slots. Please log in or create a new user if you're new")
		println("[1]: Log In")
		println("[2]: Register New User")
		println("[3]: Find User")
		println("[4]: Exit")
		val input = pollForInput()
		input match {
			case "1" => { login() }
			case "2" => { registerUser() }
			case "3" => { findUser() }
			case "4" => { exit() }
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
				println(s"********** db = your_db                                  **********")
				
				throw e
			}
			case e: Throwable => throw e
		}
	}

	def main(args: Array[String]) {
		println("Starting....\n\n")

		connectToDB()
		// todo: basic game logic

		do {
			val input = State.GetLoggedIn() match {
				case true => mainMenu()
				case false => loginScreen()
			}
		} while(State.GetContinue())

	}

}