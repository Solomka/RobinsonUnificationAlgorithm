/**
 *
 */
public class UnificationPair {

	private Token lhs;
	private Token rhs;

	public UnificationPair(Token lhs, Token rhs) {
		if (lhs == null || rhs == null) {
			throw new IllegalArgumentException("Both terms must not be null.");
		}

		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Token getLhs() {
		return lhs;
	}

	public Token getRhs() {
		return rhs;
	}

	@Override
	public String toString() {
		return String.format("UnificationPair[lhs='%s', rhs='%s']", lhs, rhs);
	}

}