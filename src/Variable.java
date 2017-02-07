public class Variable implements Token {

    /** The name of the variable. */
    private String name;

    // ------------------------------------------ Constructors

    public Variable() {
        name = null;
    }

    public Variable(String name) {
        if (name == null) {
            throw new IllegalArgumentException(
                    "The given variable name must not be null.");
        }

        this.name = name;
    }

    // ------------------------------------------ Public methods

    /**
     * <p>Returns the name of this variable.</p>
     * 
     * @return the name of this variable
     */
    public String getName() {
        return name;
    }

    // ------------------------------------------ Term methods

    public boolean occurs(Token term) {
        // In a variable, it cannot be the case that another term occurs.
        return false;
    }

    public Token substitute(Token term, Token replacement) {
        // If the user wants to substitute this variable, return the replacement.
        if (getName() != null && equals(term)) {
            return replacement;
        }

        return this;
    }

    // ------------------------------------------ Object methods

    /**
     * <p>Determines whether the given object is an equal variable. Note that
     * the only condition that it has to fulfill is to have an equal name.</p>
     *
     * @param obj the object you want to check
     *
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

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