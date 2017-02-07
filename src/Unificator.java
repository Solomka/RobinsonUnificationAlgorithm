import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Unificator {

	
	public List<TokensPair> unify(TokensPair termsToUnify) {
		/**
		 * workingSet - list of all TokensPairs to unify
		 * 
		 * Ex: TokenPair ( f(x, h(a,b)), f(b,z) )
		 *  	List<TokenPair> workingSet = { TP( f(x, h(a,b)), f(b,z) ), TP( x, b ), TP( h(a,b), z )}
		 */
		List<TokensPair> workingSet = new ArrayList<TokensPair>();
		workingSet.add(termsToUnify);
		
		/**
		 * unifier - list of replacement TokensPairs
		 * 
		 * Ex: TokenPair ( f(x, h(a,b)), f(b,z) )
		 * 		List<TokenPair> unifier = { TP( x, b ) ,TP( z,  h(a,b) )}
		 */
		List<TokensPair> unifier = new ArrayList<TokensPair>();

		for (int i = 0; i < workingSet.size(); ++i) {

			TokensPair currentPair = workingSet.get(i);
			Token lhs = currentPair.getToken1();
			Token rhs = currentPair.getToken2();

			if (!unify(lhs, rhs, workingSet, unifier)) {

				return null;
			}
		}

		return unifier;
	}

	private boolean unify(Token lhs, Token rhs, List<TokensPair> workingSet,
			List<TokensPair> unifier) {
		
		//select unification method according to the Term Type
		if (lhs instanceof Constant) {
			return unify((Constant) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Variable) {
			return unify((Variable) lhs, rhs, workingSet, unifier);
		} else if (lhs instanceof Predicate) {
			return unify((Predicate) lhs, rhs, workingSet, unifier);
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
			System.out.println("Substitution can't be executed: " + variable.toString() + " in " + term.toString());
			return false;
		}

		// execute substitution in all TokensPairs in woringSet
		substitute(variable, term, workingSet);
		substitute(variable, term, unifier);

		//add new substitution TokensPair to the result list
		unifier.add(new TokensPair(variable, term));

		return true;
	}

	private boolean unify(Predicate predicate, Token term,
			List<TokensPair> workingSet, List<TokensPair> unifier) {
		if (term instanceof Variable) {

			return unify((Variable) term, predicate, workingSet, unifier);
			//if right term is predicate too
		} else if (term instanceof Predicate) {
			Predicate rhsPredicate = (Predicate) term;

			//if predicates has different names of vars number => false
			if (!predicate.getName().equals(rhsPredicate.getName())
					|| predicate.getArgs().size() != rhsPredicate.getArgs()
							.size()) {
				System.out.println("Substitution can't be executed 'cause predicates are different");
				return false;
			}			
						
			/**
			 * create TokenPairs from leftPredicate args and rightPredicte args
			 * 
			 * Ex: TokenPair ( f(x, h(a,b)), f(b,z) )
			 * 		List<TokenPair> workingSet = { TP( f(x, h(a,b)), f(b,z) ), TP( x, b ), TP( h(a,b), z )}
			 */
			for (Iterator<Token> lhsArgs = predicate.getArgs().iterator(), rhsArgs = rhsPredicate
					.getArgs().iterator(); lhsArgs.hasNext()
					&& rhsArgs.hasNext();) {

				workingSet.add(new TokensPair(lhsArgs.next(), rhsArgs.next()));
			}

			return true;
		}

		return false;
	}

	//execute substitution in Tokens according to their types
	private void substitute(Token variable, Token replacement,
			List<TokensPair> list) {
		for (int i = 0; i < list.size(); i++) {

			TokensPair currentPair = list.get(i);
			Token lhs = currentPair.getToken1();
			Token rhs = currentPair.getToken2();

			list.set(i, new TokensPair(lhs.substitute(variable, replacement),
					rhs.substitute(variable, replacement)));
		}
	}

}