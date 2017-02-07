import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Predicate implements Token {

	/** The name of this predicate, e.g. "sells" or "produces" */
	private String name;

	private List<Token> args;

	// ------------------------------------------ Constructor

	public Predicate(){
		
	}
	public Predicate(String name, Token... args) {
		this(name, Arrays.asList(args));
	}

	public Predicate(String name, List<Token> args) {
		if (name == null) {
			throw new IllegalArgumentException(
					"The given predicate name must not be null.");
		}

		this.name = name;
		this.args = args;
	}

	// ------------------------------------------ Public methods

	public String getName() {
		return name;
	}

	public List<Token> getArgs() {
		return Collections.unmodifiableList(args);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setArgs(List<Token> args) {
		this.args = args;
	}
	

	// ------------------------------------------ Term methods

	
	public boolean occurs(Token term) {
		for (Token arg : args) {
			if (arg.equals(term)) {
				return true;
			}
		}

		return false;
	}

	public Token substitute(Token term, Token replacement) {
		// The only thing that you can substitute in a predicate is an argument,
		// so we just have to iterate over all arguments and propagate the
		// substitution request.
		List<Token> substitutedArgs = new ArrayList<Token>(args.size());
		for (Token arg : args) {
			substitutedArgs.add(arg.substitute(term, replacement));
		}

		return new Predicate(name, substitutedArgs);
	}

	// ------------------------------------------ Object methods

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Predicate predicate = (Predicate) obj;

		if (!getArgs().equals(predicate.getArgs()))
			return false;
		if (!getName().equals(predicate.getName()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getArgs().hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuffer predicate = new StringBuffer(getName()).append("(");

		Iterator args = getArgs().iterator();
		while (args.hasNext()) {
			predicate.append(args.next());

			if (args.hasNext()) {
				predicate.append(", ");
			}
		}

		predicate.append(")");

		return predicate.toString();
	}

}