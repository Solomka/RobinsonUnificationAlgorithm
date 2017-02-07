import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Test data:
	 * 
	 * f(f(f(a,b)),h(a,h(c)))
	 * f(y,z)
	 * 
	 * f(h(h(h(a,b)),c),z)
     * f(x,h(a,x))
	 * 
	 */
	public static void main(String[] args) {

		int ifTryAgain = 0;
		
		while (true) {

			System.out.println("Robinson's unification algorithm");
			System.out.println("\n");

			System.out.println("Type first term: ");
			String firstLexemStr = scanner.next();
			// System.out.println("firstTermStr: " + firstTermStr);

			System.out.println("Type second term: ");
			String secondLexemStr = scanner.next();
			// System.out.println("secondTermStr: " + secondTermStr);

			LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

			Token token1 = lexicalAnalyzer.analyzeLexem(firstLexemStr);
			// System.out.println("token1: "+ token1.toString());

			Token token2 = lexicalAnalyzer.analyzeLexem(secondLexemStr);
			// System.out.println("token2: " + token2.toString());

			// check if lexems are syntactically correct
			if (token1 != null && token2 != null) {

				TokensPair uP = new TokensPair(token1, token2);
				System.out.println(uP.toString());

				Unificator s = new Unificator();
				List<TokensPair> uPR = s.unify(uP);

				// check if unification can be processed
				if (uPR == null) {
					System.out.println("Tokens can't be unified");
				} else {
					for (TokensPair u : uPR) {
						System.out.println(u.toString());
					}
				}
			} else {
				System.out.println("Incorrect lexems' syntax. Try again.");
			}

			// if try again

			System.out.println("\nType 1 if you want to try again\n");
			System.out.println("Type 0 if you want to finish\n");

			ifTryAgain = scanner.nextInt();

			if (ifTryAgain == 0) {
				break;
			}

	}

	}
}