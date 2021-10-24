class User(private var id: Int = -1, private var username: String = "null") {
	private var balance: Int = 0

	def GetBalance(): Int = { this.balance }
	def SetBalance(balance: Int) { this.balance = balance.max(0) }

	def GetUsername(): String = { this.username }
	def SetUsername(username: String) { this.username = username }

	// convenience functions
	private def HandleBalance(amount: Int) {
		val balance = this.GetBalance()
		this.SetBalance(balance + amount)
	}
	def AddBalance(amount: Int) {
		this.HandleBalance(amount)
	}
	def RemoveBalance(amount: Int) {
		this.HandleBalance(-amount)
	}

	def HasBalance(amount: Int): Boolean = { this.GetBalance() >= amount }
}