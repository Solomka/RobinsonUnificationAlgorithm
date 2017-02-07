public interface Token {

	public boolean occurs(Token token);

	public Token substitute(Token oldToken, Token newToken);

	public String toString();

}