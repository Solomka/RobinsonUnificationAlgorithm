/**
 *
 */
public class TokensPair {

	private Token lhs;
	private Token rhs;

	public TokensPair(){
		
	}
	
	public TokensPair(Token lhs, Token rhs) {
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
		return String.format("TokensPair[lhs='%s', rhs='%s']", lhs, rhs);
	}

}