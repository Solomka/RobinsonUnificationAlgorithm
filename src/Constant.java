public class Constant implements Token {

    /** The name of this constant, like, for example, "johndoe" or whatever. */
    private String name;

    // ------------------------------------------ Constructors

    public Constant(String name) {
        if (name == null) {
            throw new IllegalArgumentException(
                    "The given name of the constant must not be null.");
        }
        
        this.name = name;
    }

    // ------------------------------------------ Public methods

    public String getName() {
        return name;
    }

    // ------------------------------------------ Term methods

    public boolean occurs(Token term) {
        // No other term can occur in a constant, so it's always going to be
        // false regardless of whatever the other term might be.
        return false;
    }

    public Token substitute(Token term, Token replacement) {
        // You cannot substitute anything in a constant, so this method just returns
        // this constant again regardless of the substitution parameters. However, it's
        // not an invalid operation, i.e. there's no need to raise an exception, it just
        // doesn't do anything useful.
        return this;
    }

    // ------------------------------------------ Object methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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