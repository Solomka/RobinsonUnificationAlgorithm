import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class RobinsonUnificationStrategy{

	/** The logger instance for this class. */
	private final Logger logger = Logger.getLogger(getClass().getName());

	// ------------------------------------------ UnificationStrategy methods

	public List<UnificationPair> unify(UnificationPair termsToUnify) {
		return unify(Arrays.asList(termsToUnify));
	}

	public List<UnificationPair> unify(List<UnificationPair> termsToUnify) {
		List<UnificationPair> workingSet = new ArrayList<UnificationPair>(
				termsToUnify);
		List<UnificationPair> unifier = new ArrayList<UnificationPair>();

		// Note that I'm intentionally not using the enhanced for loop
		// as I couldn't modify this list otherwise within the iteration
		// (e.g. due to substitution).
		for (int i = 0; i < workingSet.size(); ++i) {
			// Retrieve the current pair of terms ..
			UnificationPair currentPair = workingSet.get(i);
			Token lhs = currentPair.getLhs();
			Token rhs = currentPair.getRhs();

			// .. and try to unify them
			if (!unify(lhs, rhs, workingSet, unifier)) {
				// If you can't unify them, return null instead, which indicates
				// that unification isn't possible.
				return null;
			}
		}

		return unifier;
	}

	// ------------------------------------------ Private utility methods

	private boolean unify(Token lhs, Token rhs, List<UnificationPair> workingSet,
			List<UnificationPair> unifier) {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE,
					"    Attempting to unify the terms ''{0}'' and ''{1}''.",
					new Object[] { lhs, rhs });
		}

		// I'm aware of the fact that this bit of code looks like it would be
		// appropriate to introduce
		// a separate method in the term interface that enables terms to unify
		// themselves with other
		// terms. In doing so, I could get rid of this
		// "if-then-else-dispatch-mess", but as I've
		// already mentioned in the documentation of the UnificationStrategy
		// interface, there's no
		// single unification algorithm as there is no single sorting algorithm
		// either.
		// The drawback of this approach is of course the need to adapt every
		// single algorithm
		// implementation, if one introduces a new term. However, I can't think
		// of any such term and
		// I think it's more likely that one wants to implement a different
		// unification algorithm
		// (especially as the Robinson unification algorithm is not the fastest
		// one), rather than to
		// introduce a new kind of term.
		if (lhs instanceof Constant) {
			return unify((Constant) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Variable) {
			return unify((Variable) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Predicate) {
			return unify((Predicate) lhs, rhs, workingSet, unifier);
		}

		// One could possibly also argue that if this happens an exceptions
		// should be thrown instead
		// (e.g. an IllegalStateException or OperationNotSupportedException),
		// however, I think it's
		// not necessarily invalid to pass something else as parameter, this
		// strategy just can't unify
		// it, that's it.
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(
					Level.SEVERE,
					"    Cannot unify the terms as the kind of term ''{0}'' is unknown.",
					lhs);
		}

		return false;
	}

	private boolean unify(Constant constant, Token term,
			List<UnificationPair> workingSet, List<UnificationPair> unifier) {
		if (term instanceof Variable) {
			// If we're unifying this constant with a variable, just delegate
			// this
			// unification call to the variable as this has already been
			// implemented
			// below.
			return unify((Variable) term, constant, workingSet, unifier);
		}

		// Note that there is only one other case where unification succeeds if
		// the
		// left-hand side term is a constant, namely if we're unifying two equal
		// constants ("a = a").
		if (constant.equals(term)) {
			return true;
		}

		// Otherwise, this left-hand side constant and the right-hand side term
		// can't be unified.
		return false;
	}

	private boolean unify(Variable variable, Token term,
			List<UnificationPair> workingSet, List<UnificationPair> unifier) {
		// If we're unifying the same variable, like "X = X", unification
		// succeeds, but
		// we don't have to anything, i.e. we basically skip this pair of
		// variables
		// and carry on with unification afterwards.
		if (variable.equals(term)) {
			return true;
		}

		// If this variable occurs somewhere within the given term,
		// unification is not possible (e.g. "X = f(X)", which is a
		// cyclic term).
		if (term.occurs(variable)) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(
						Level.FINE,
						"    Cannot unify the variable ''{0}'' and the term "
								+ "''{1}'' as the variable occurs in the term.",
						new Object[] { variable, term });
			}

			return false;
		}

		// Otherwise unification succeeds, we found a new pair in our unifier
		// and
		// now have to process it (i.e. substitute any occurrences of this
		// variable
		// in the working set and the current state of the most general
		// unifier).
		substitute(variable, term, workingSet);
		substitute(variable, term, unifier);

		// Register a new pair in our unifier
		unifier.add(new UnificationPair(variable, term));

		return true;
	}

	private boolean unify(Predicate predicate, Token term,
			List<UnificationPair> workingSet, List<UnificationPair> unifier) {
		if (term instanceof Variable) {
			// If we're unifying this predicate with a variable, just swap
			// parameters and issue another unification call as this has already
			// been implemented above.
			return unify((Variable) term, predicate, workingSet, unifier);
		} else if (term instanceof Predicate) {
			Predicate rhsPredicate = (Predicate) term;

			// If either the name or the number of arguments don't match ..
			if (!predicate.getName().equals(rhsPredicate.getName())
					|| predicate.getArgs().size() != rhsPredicate.getArgs()
							.size()) {
				// .. unification fails.
				return false;
			}

			// Otherwise try to unify all arguments ..
			for (Iterator<Token> lhsArgs = predicate.getArgs().iterator(), rhsArgs = rhsPredicate
					.getArgs().iterator(); lhsArgs.hasNext()
					&& rhsArgs.hasNext();) {
				// .. by adding all those pairs to our working set. In doing so,
				// it will eventually be processed.
				workingSet.add(new UnificationPair(lhsArgs.next(), rhsArgs
						.next()));
			}

			return true;
		}

		// Otherwise, unification fails.
		return false;
	}

	private void substitute(Token variable, Token replacement,
			List<UnificationPair> list) {
		for (int i = 0; i < list.size(); i++) {
			// Retrieve the current pair of terms ..
			UnificationPair currentPair = list.get(i);
			Token lhs = currentPair.getLhs();
			Token rhs = currentPair.getRhs();

			// .. and replace that pair with the substitution.
			list.set(i,
					new UnificationPair(lhs.substitute(variable, replacement),
							rhs.substitute(variable, replacement)));
		}
	}

}