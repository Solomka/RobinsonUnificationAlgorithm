import java.util.List;

/**
 *
 * <p>
 * Note that many implementations of a unification algorithm out there implement
 * the necessary bits within the term classes, like, for example, a variable
 * knows how to unify itself with another term, and so on. However, I've decided
 * to use a different approach. In this case, implementations of this strategy
 * interface, and only those implementations, know the details about
 * unification. A term only has to know the most basic bits of it, like whether
 * it occurs in a different term or not. In doing so, one is basically able to
 * use a different unification algorithm if he/she wishes to. So basically I
 * thought very much like strings don't know how to sort themselves within a
 * collection, terms shouldn't know how unification is done (however, strings do
 * know how to compare themselves with different strings, so do my terms know
 * things about occurrences and substitutions).
 * </p>
 * 
 */
public interface UnificationStrategy {

	public List<UnificationPair> unify(UnificationPair... termsToUnify);

	public List<UnificationPair> unify(List<UnificationPair> termsToUnify);

}