import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LexicalAnalyzer {

	// enum - private static final enum by default
	// token pattern - formal description of lexems' class that
	private enum TokenPattern {
		VARIABLE, CONSTANT, FUNCTION
	};

	private static final List<String> VARIABLE = Arrays.asList("x", "y", "z");
	private static final List<String> CONSTANT = Arrays.asList("a", "b", "c");
	private static final List<String> FUNCTION = Arrays.asList("f", "h");

	private Token token;

	public LexicalAnalyzer(String lexem) {
		token = analyzeLexem(lexem);
		System.out.println("LexicalAnalyzer: " + token);
	}

	private String[] scanString(String lexem) {
		return lexem.split("[,()]");
	}
	
	
	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	private Token analyzeLexem(String lexem) {

		String[] parsedLexem = scanString(lexem);
		
		for(String s: parsedLexem){		
		System.out.println("s: "+ s);
		}

		// if one of the symbols is "" return NULL

		for (String str : parsedLexem) {
			if (str == null || str.isEmpty()) {
				System.out.println("NULL");
				return null;
			}
		}

		// if CONSTANT or VARIABLE
		if (parsedLexem.length == 1) {
			
			System.out.println("parsedLexem.length == 1");

			if (defineTokenPattern(parsedLexem[0]) == TokenPattern.VARIABLE) {
				token = new Variable(parsedLexem[0]);
			} else if (defineTokenPattern(parsedLexem[0]) == TokenPattern.CONSTANT) {
				token = new Constant(parsedLexem[0]);
			} else {
				return null;
			}

		}

		// FUNCTION or NULL
		else {
			System.out.println("parsedLexem.length NOT 1");
			// if first symbol not a "f, h" return null
			if (defineTokenPattern(parsedLexem[0]) != TokenPattern.FUNCTION) {
				System.out.println("defineTokenPattern(parsedLexem[0]) != TokenPattern.FUNCTION");
				return null;
			}

			// if paranthes not match return null
			else if (!isParenthesisMatch(lexem)) {
				System.out.println("!isParenthesisMatch(lexem)");
				return null;
			} else {
				System.out.println("everything is good");
				// main logic Here...
				for (String symbol : parsedLexem) {					
					
					if (defineTokenPattern(symbol) != TokenPattern.VARIABLE
							&& defineTokenPattern(symbol) != TokenPattern.CONSTANT
							&& defineTokenPattern(symbol) != TokenPattern.FUNCTION) {
						System.out.println("LOL");
						return null;
					}
				}
				token = constractPredicate(parsedLexem);
				
			}
			
		}		
		
		return token;
	}

	private TokenPattern defineTokenPattern(String lexem) {

		if (VARIABLE.contains(lexem)) {
			return TokenPattern.VARIABLE;
		} else if (CONSTANT.contains(lexem)) {
			return TokenPattern.CONSTANT;
		} else if (FUNCTION.contains(lexem)) {
			return TokenPattern.FUNCTION;
		} else {
			return null;
		}
	}

	private boolean isParenthesisMatch(String str) {

		Stack<Character> stack = new Stack<Character>();

		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (c == '(') {
				stack.push(c);
			}

			else if (c == ')') {
				if (stack.empty())
					return false;
				else if (stack.peek() == '(')
					stack.pop();
				else
					return false;
			}
		}
		return stack.empty();
	}
/*	
	private Predicate constractComplexPredicate(String lexem){
		
		String[] parsedLexem = scanString(lexem);
		
		List<Token> args = new ArrayList<Token>();
		
		Predicate predicate = new Predicate();
		predicate.setName(parsedLexem[0]);
		
		for(int i = 1; i< parsedLexem.length; ++i){
			if(defineTokenPattern(parsedLexem[i]) == TokenPattern.VARIABLE){
				args.add(new Variable(parsedLexem[i]));
			}
			else if(defineTokenPattern(parsedLexem[i]) == TokenPattern.CONSTANT){
				args.add(new Constant(parsedLexem[i]));
			}
			else if(defineTokenPattern(parsedLexem[i]) == TokenPattern.FUNCTION)
						
		}
		
		predicate.setArgs(args);		
	
		return predicate;
	}
	*/
	private Predicate constractPredicate(String [] parsedLexem){
		System.out.println("Construct simple predicate");
		List<Token> args = new ArrayList<Token>();
		
		Predicate predicate = new Predicate();
		predicate.setName(parsedLexem[0]);
		
		for(int i = 1; i< parsedLexem.length; ++i){
			if(defineTokenPattern(parsedLexem[i]) == TokenPattern.VARIABLE){
				args.add(new Variable(parsedLexem[i]));
			}
			else if(defineTokenPattern(parsedLexem[i]) == TokenPattern.CONSTANT){
				args.add(new Constant(parsedLexem[i]));
			}
						
		}
		
		predicate.setArgs(args);		
	
		return predicate;
	}

}
