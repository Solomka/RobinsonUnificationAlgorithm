public class Constant implements Token {

	private String name;

	public Constant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean occurs(Token term) {
		return false;
	}

	public Token substitute(Token term, Token replacement) {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Constant constant = (Constant) o;
		return name.equals(constant.getName());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

}