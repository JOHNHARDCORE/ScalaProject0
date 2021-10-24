object State {
	private var continue = true

	def SetContinue(new_state: Boolean) { this.continue = new_state }
	def GetContinue(): Boolean = { this.continue }
}