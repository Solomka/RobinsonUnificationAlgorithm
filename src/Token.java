public interface Token {

    /**
     * <p>Determines whether the given term occurs in this term somehow. In doing so,
     * you're able to prevent unification attempts of the form "A = f(A)", i.e. it
     * prevents the usage of so-called cyclic terms. Otherwise you'd end up with
     * an infinite recursion (which would of course at some point raise an
     * exception).</p>
     * 
     * @param term the term that you want to check
     * 
     * @return
     */
    public boolean occurs(Token term);

    /**
     * <p>Substitutes the given term with the replacement and returns a copy of this
     * term as a result of this operation. So, for example, if you're substituting
     * the variable X in the function f(X) with the constant c as replacement, you'll
     * end up with a new copy of this function term, "f(c)".</p>
     *
     * @param term the term that you want to replace
     * @param replacement the term that you want to replace the other term with
     *
     * @return a copy of this term after the substitution
     */
    public Token substitute(Token term, Token replacement);
    
    public String toString();

}