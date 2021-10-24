import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn.readLine

object App {

	def main(args: Array[String]) {
		println("Starting....\n\n")

		do {
			println("[1]: Make New User")
			println("[2]: Exit")

			val input = readLine(">Enter in an option: ")

			input match {
				case "1" => println("do this later")
				case "2" => { println("exiting..."); State.SetContinue(false) }
				case _ => println("invalid option")
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