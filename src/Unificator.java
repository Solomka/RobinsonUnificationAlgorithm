import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Unificator {

	/** The logger instance for this class. */
	private final Logger logger = Logger.getLogger(getClass().getName());

	public List<TokensPair> unify(TokensPair termsToUnify) {
		return unify(Arrays.asList(termsToUnify));
	}

	public List<TokensPair> unify(List<TokensPair> termsToUnify) {
		List<TokensPair> workingSet = new ArrayList<TokensPair>(termsToUnify);
		List<TokensPair> unifier = new ArrayList<TokensPair>();

		for (int i = 0; i < workingSet.size(); ++i) {

			TokensPair currentPair = workingSet.get(i);
			Token lhs = currentPair.getLhs();
			Token rhs = currentPair.getRhs();

			if (!unify(lhs, rhs, workingSet, unifier)) {

				return null;
			}
		}

		return unifier;
	}

	private boolean unify(Token lhs, Token rhs, List<TokensPair> workingSet,
			List<TokensPair> unifier) {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE,
					"    Attempting to unify the terms ''{0}'' and ''{1}''.",
					new Object[] { lhs, rhs });
		}

		if (lhs instanceof Constant) {
			return unify((Constant) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Variable) {
			return unify((Variable) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Predicate) {
			return unify((Predicate) lhs, rhs, workingSet, unifier);
		}

		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(
					Level.SEVERE,
					"    Cannot unify the terms as the kind of term ''{0}'' is unknown.",
					lhs);
		}

		return false;
	}

	private boolean unify(Constant constant, Token term,
			List<TokensPair> workingSet, List<TokensPair> unifier) {
		if (term instanceof Variable) {

			return unify((Variable) term, constant, workingSet, unifier);
		}

		if (constant.equals(term)) {
			return true;
		}

		return false;
	}

	private boolean unify(Variable variable, Token term,
			List<TokensPair> workingSet, List<TokensPair> unifier) {

		if (variable.equals(term)) {
			return true;
		}

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

		substitute(variable, term, workingSet);
		substitute(variable, term, unifier);

		unifier.add(new TokensPair(variable, term));

		return true;
	}

	private boolean unify(Predicate predicate, Token term,
			List<TokensPair> workingSet, List<TokensPair> unifier) {
		if (term instanceof Variable) {

			return unify((Variable) term, predicate, workingSet, unifier);
		} else if (term instanceof Predicate) {
			Predicate rhsPredicate = (Predicate) term;

			if (!predicate.getName().equals(rhsPredicate.getName())
					|| predicate.getArgs().size() != rhsPredicate.getArgs()
							.size()) {

				return false;
			}

			for (Iterator<Token> lhsArgs = predicate.getArgs().iterator(), rhsArgs = rhsPredicate
					.getArgs().iterator(); lhsArgs.hasNext()
					&& rhsArgs.hasNext();) {

				workingSet.add(new TokensPair(lhsArgs.next(), rhsArgs.next()));
			}

			return true;
		}

		return false;
	}

	private void substitute(Token variable, Token replacement,
			List<TokensPair> list) {
		for (int i = 0; i < list.size(); i++) {

			TokensPair currentPair = list.get(i);
			Token lhs = currentPair.getLhs();
			Token rhs = currentPair.getRhs();

			list.set(i, new TokensPair(lhs.substitute(variable, replacement),
					rhs.substitute(variable, replacement)));
		}
	}

}