import java.util.List;
import java.util.Scanner;

public class Test {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int t = 0;
		
		do{
		
		System.out.println("Robinson's unification algorithm");
		System.out.println("\n");

		System.out.println("Type first term: ");
		String firstTermStr = scanner.next();
		System.out.println("firstTermStr: " + firstTermStr);

		System.out.println("Type second term: ");
		String secondTermStr = scanner.next();
		System.out.println("secondTermStr: " + secondTermStr);

		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(firstTermStr);
		Token token1 = lexicalAnalyzer.getToken();
		//System.out.println("token1: "+ token1.toString());

		LexicalAnalyzer lexicalAnalyzer2 = new LexicalAnalyzer(secondTermStr);
		Token token2 = lexicalAnalyzer2.getToken();
		//System.out.println("token2: " + token2.toString());

		if(token1 != null && token2 != null){
		UnificationPair uP = new UnificationPair(token1, token2);
		System.out.println("unificationPair: " + uP.toString());

		RobinsonUnificationStrategy s = new RobinsonUnificationStrategy();
		List<UnificationPair> uPR = s.unify(uP);

		if (uPR == null) {
			System.out.println("Can't be unified");
		} else {
			for (UnificationPair u : uPR) {
				System.out.println("- : " + u.toString());
			}
		}
		}
		else{
			System.out.println("Incorrect lexems. Try again.");
			
		}
		System.out.println("vvedit 1 dlya prodovjennya.");
		t = scanner.nextInt();
		
		}
		while (t == 1);	

	}
}