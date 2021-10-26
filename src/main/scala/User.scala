class User(private var username: String = "Guest", private var password: String = "password") {
	private var balance: Int = 500
	private var id: Int = -1

	def this(id: Int, username: String, password: String) {
		this(username, password)
		this.id = id
	}

	def GetID(): Int = { this.id }
	def SetID(id: Int) { this.id = id }

	def GetBalance(): Int = { this.balance }
	def SetBalance(balance: Int) { this.balance = balance.max(0) }

	def GetUsername(): String = { this.username }
	def SetUsername(username: String) { this.username = username }

	def GetPassword(): String = { this.password }
	def SetPassword(password: String) { this.password = password }

	def PrintInformation() {
		println("---------------------------------")
		println(s"Username: ${this.GetUsername()}")
		println(s"Balance: $$${this.GetBalance()}")
		println("---------------------------------")
	}
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