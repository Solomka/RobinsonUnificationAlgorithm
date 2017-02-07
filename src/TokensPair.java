/**
 *
 */
public class TokensPair {

	private Token token1;
	private Token token2;

	public TokensPair(){
		
	}
	
	public TokensPair(Token token1, Token token2) {
		this.token1 = token1;
		this.token2 = token2;
	}

	public Token getToken1() {
		return token1;
	}

	public Token getToken2() {
		return token2;
	}

	@Override
	public String toString() {
		return String.format("TokensPair(token1='%s', token2='%s')", token1, token2);
	}

}