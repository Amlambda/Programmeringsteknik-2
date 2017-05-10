import java.util.*;
import java.io.*;
/**
 * A numeric calculator
 * @version 2016-08-11
 */
public class Calculator {
  
  private Parser parser;
  private Stokenizer tokenizer;
  private Map<String, Double> variables;  
  
  public Calculator(Stokenizer tokenizer) {
    this.tokenizer = tokenizer;
    variables = new TreeMap<String, Double>(); // Variable values
    variables.put("PI", Math.PI);              // Predefined constant
    variables.put("E", Math.E);                // Predefined constant
    variables.put("ans", new Double(0.));      // Predefined answer variable 
    parser = new Parser(tokenizer, variables);    
  }
  
  public static void main(String[] args) 
     throws IOException, SyntaxException, EvaluationException
  {
     System.out.println("Math wiz is on!");
     Calculator calc = new Calculator(new Stokenizer());
     while (true) {
        calc.statement();
     }
  }
 
  /**
   * Handles one statement
   */
  public void statement()
     throws IOException, SyntaxException, EvaluationException
  {
    try {
       line();
    } catch (SyntaxException syntaxException) {
    	System.out.println(syntaxException.getMessage());
    	tokenizer.nextToken();
      // Add handling of syntax errors errors here
     // throw syntaxException;
    } catch (EvaluationException evaluationException) {
    	System.out.println(evaluationException.getMessage());
    	tokenizer.nextToken();
      // Add handling of evaluation errors here
     // throw evaluationException;
    }    
  } 

  
  private void line() 
     throws IOException, SyntaxException, EvaluationException
  {
    do {                                            // Skip empty lines  
      System.out.print("> ");
      tokenizer.nextToken();
    } while (tokenizer.isEOL());
    
    String command = tokenizer.getToken();         // First token
    if (command.equals("quit") || 
        tokenizer.isEOS()) {                       // could be the quit command,
      System.out.println("Bye!");
      System.exit(0);
    } else if (command.equals("vars")) {           // the vars command,
      for (Map.Entry<String, Double> entry : 
              variables.entrySet()) {
        System.out.println(entry.getKey()+ "\t : " + 
                           entry.getValue());
      }
      tokenizer.nextToken();
    } else if (command.equals("file")) {           // a file command or
       tokenizer.nextToken();
       fileInput(tokenizer.getToken()); 
    } else {
      double parsed = parser.assignment();         // the first element in an expression.
      if (!tokenizer.isEOL()) {
        throw new SyntaxException("Expected EOL (or something else...)");
      }
      System.out.println(parsed);  
      variables.put("ans", new Double(parsed));
    }
    if (!tokenizer.isEOL()) {
      throw new SyntaxException("Expected EOL");
    }
  }
  
  /**
   * Takes the input to the calculator from a file.
   * The method is intended mainly for debugging reason.
   * It displays the input lines and results stepwise.
   * Any syntax or evaluation error will terminate the reading from the file
   * The commands (quit, file and vars) can not be used in a file
   * @throws SyntaxException
   * @throws EvaluationException
   * @throws IOException
   * @param fileName name of the file to be used as input
   */
  private void fileInput(String fileName)
    throws IOException
  {
    Scanner scan = new Scanner(System.in);
    if (!tokenizer.isWord()) {
      throw new SyntaxException("Expected filename. Found: " + tokenizer.getToken());   
    }
    File input = new File(fileName);
    if (!input.exists()) {
      throw new EvaluationException("The file '" + fileName + "' does not exist");
    }
    System.out.println();
    tokenizer.nextToken();
    Stokenizer strTokenizer = null;
    Scanner fsc = new Scanner(input);
    while (fsc.hasNextLine()) {
      String line = fsc.nextLine();
      String comment = "";
      int commentIndex = line.indexOf("%");
      if (commentIndex>=0) {
        comment = line.substring(commentIndex);
        line = line.substring(0,commentIndex);
      }
      line = line.trim();
      if (line.length()==0 && comment.length()>0) {
        System.out.println(comment);
      } else if (line.length()>0) {
          System.out.format("Input  : %-35s  %s", line, comment);
          scan.nextLine();
          strTokenizer = new Stokenizer(line);
          Parser parser = new Parser(strTokenizer, variables);
          strTokenizer.nextToken();
          String command = tokenizer.getToken();
          double value = parser.assignment();
          System.out.format("%-46s  %s","Result : ", value); 
          variables.put("ans", value);
          scan.nextLine();
        }         
    }
    System.out.println("* End of file input *");
  }
  
}

