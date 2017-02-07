import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

	private static Scanner scanner = new Scanner(System.in);
/*	
	private static TokensPair generateTokensPair(){
		//example 
		
				//first predicate				
				List<Token> predicate1Terms = new ArrayList<Token>();	
				
				Token con1 = new Constant("b");			
				Token con2 = new Variable("z");	
				
				predicate1Terms.add(con1);
				predicate1Terms.add(con2);
				
				Predicate predicate1 = new Predicate("f", predicate1Terms);
				
				//second predicate
				List<Token> predicate2Terms = new ArrayList<Token>();	
				
				Token var1 = new Variable("x");
				
				List<Token> predicate23Terms = new ArrayList<Token>();
				
				Token tt = new Constant("a");
				Token tt1 = new Constant("b");
				
				predicate23Terms.add(tt);
				predicate23Terms.add(tt1);
						
				Token var2 = new Predicate("h",predicate23Terms );
				
				predicate2Terms.add(var1);
				predicate2Terms.add(var2);
				
				Predicate predicate2 = new Predicate("f", predicate2Terms);
				
				TokensPair uP = new TokensPair(predicate1, predicate2);
				
				return uP;
	}
	*/
	public static void main(String[] args) {
/*
		TokensPair tP = generateTokensPair();
		System.out.println(tP.toString());
		
		Unificator s = new Unificator();
		List<TokensPair> uPR = s.unify(tP);

		// check if unification can be processed
		if (uPR == null) {
			System.out.println("Tokens can't be unified");
		} else {
			for (TokensPair u : uPR) {
				System.out.println(u.toString());
			}
		}
	*/	
		//f(x,y,h(a,b,z), h(z,a))
		//f(a,b,z,x)
		
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