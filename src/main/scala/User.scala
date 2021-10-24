class User(private var id: Int, private var username: String) {
	private var balance: Int = 0

	def GetBalance(): Int = { this.balance }
	def SetBalance(balance: Int) { this.balance = balance }

	def GetUsername(): String = { this.username }
	def SetUsername(username: String) { this.username = username }
}