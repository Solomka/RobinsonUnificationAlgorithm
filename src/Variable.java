public class Variable implements Token {

	private String name;

	public Variable() {
		name = null;
	}

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public boolean occurs(Token token) {
		return false;
	}

	public Token substitute(Token token, Token replacement) {
		if (getName() != null && equals(token)) {
			return replacement;
		}

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Variable variable = (Variable) obj;
		if (name == null && variable.getName() == null) {
			return true;
		}

		return name.equals(variable.getName());
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return getName() != null ? getName() : "_";
	}

}