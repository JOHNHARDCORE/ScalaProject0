object State {
	private var continue = true
	private var logged_in = false
	private var user: String = ""

	def SetContinue(new_state: Boolean) { this.continue = new_state }
	def GetContinue(): Boolean = { this.continue }

	def SetUser(user: String) { this.user = user }
	def GetUser(): String = { this.user }

	def SetLoggedIn(new_state: Boolean) { this.logged_in = new_state }
	def GetLoggedIn(): Boolean = { this.logged_in }
}