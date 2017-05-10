/* 
 * File: Stokenizer
 */

import java.io.*;

/**
 * The <code>Stokenizer</code> takes an input stream and parses it into tokens to be
 * red one at a time. The following tokens may occur:
 * <ul>
 * <li> <em>numbers</em> (i. e. a sequence of digits possibly with a decimal point)
 * <li> <em>words</em> (i e sequences of letters and digits starting with a letter)
 * <li> <em>EOL</em> (end of line) 
 * <li> <em>EOS</em> (end of stream i. e. end of file or end of string)
 * <li> <em>%</em> (EOL i.e. makes it possible to write comments on input lines)
 * <li> other characters
 * </ul>
 * Spaces separate tokens but are otherwise ignored<br>
 * Note: Negative numbers are <em>two</em> tokens (a minus character and a number)
 * <p>
 * The class is an interface to the standard class 
 * <a href="http://download.oracle.com/javase/6/docs/api/java/io/StreamTokenizer.html">
 * StreamTokenizer</a>.
 * <p>
 * <strong>Example</strong>: The string 
 * <pre>
 *   "-123 +   xyc*-12\n"
 * </pre>
 * is parsed into the following tokens
 * <ol>
 * <li><code>-</code> (a <em>character</em>)
 * <li><code>123</code>  (a <em>number</em>)
 * <li><code>+</code> (a <em>character</em>)
 * <li><code>xyz</code> (a <em>word</em>)
 * <li><code>*</code> (a <em>character</em>)
 * <li><code>-</code> (a <em>character</em>)
 * <li><code>12</code> (a <em>number</em>)
 * <li><code>EOL</code> (an <em>EOL</em>-token)
 * <li><code>EOS</code> (an <em>EOS</em>-token)
 * </ol>
 * @author Tom Smedsaas
 * @version 2015-04-18
 * <ul>
 * <li>
 * Sets a default comment character (%)
 * <li> 
 * Throws <code>StokenizerException</code> 
 * instead of <code>RuntimeException</code>
 * 
 * </ul>
 */
public class Stokenizer extends StreamTokenizer {

   public class StokenizerException 
      extends RuntimeException {
      public StokenizerException(String msg) {
         super(msg);
      }
   }

 /**
  * Code for word-tokens
  */
 public final static int AWORD = StreamTokenizer.TT_WORD;
 /**
  * Code for number-tokens
  */
 public final static int ANUMBER = StreamTokenizer.TT_NUMBER;
 /**
  * Code for EOL-tokens
  */
 public final static int EOL = StreamTokenizer.TT_EOL;
 /**
  * Code for EOS-tokens
  */
 public final static int EOS = StreamTokenizer.TT_EOF;

 private String previousToken = null;
 
 /**
  * Creates a <code>Stokenizer</code> and ties it to a <code>Reader</code><br>
  * This constructor is primarily intended for internal use.
  */
 public Stokenizer(Reader r) {
  super(r); 
  ordinaryChar('-');
  ordinaryChar('/');
  ordinaryChar('\'');
  ordinaryChar('"');
  ordinaryChar('&');
  commentChar('%');
  char c = '"'; // To fool a2ps
  eolIsSignificant(true);
 }
 
 /**
  * Creates a <code>Stokenizer</code> and ties it to the standard input<br>
  */
 public Stokenizer() {
  this(new BufferedReader(new InputStreamReader(System.in)));
 }
 
 /**
  * Creates a <code>Stokenizer</code> and ties it to a <code>String</code>.<br>
  * @param source String to be tokenized
  */
 public Stokenizer(String source) {
  this(new BufferedReader(new StringReader(source)));
 }
 
 /**
  * Creates a <code>Stokenizer</code> and ties it to a <code>FileReader</code>.<br>
  * 
  * <p>
  * 
  * <strong>Example:</strong>
  * <pre>
  * Stokenizer st = new Stokenizer(new FileReader("myInputFile"));
  * </pre>
  * (note that <code>new FileReader</code> may cause an IOException - must be handled in some way)
  * @param fileReader a reference to a created FileReader object
  */
 public Stokenizer(FileReader fileReader) {
  this(new BufferedReader(fileReader));
 }

 /**
  * Moves to next token. <br>
  * Note: This must must be called before any other method is called
  * @throws StokenizerException in case of IOException
  */
 public int nextToken() {
   if (previousToken==null)
     previousToken = "";
   else
     previousToken = getToken();
  try {
   super.nextToken();
  } catch (IOException e) {
   throw new StokenizerException("IOException - should not happen ...");
  }
  return ttype;
 }

 /**
  * Checks if current token is a number
  * @return <code>true</code> if current token is a number, else <code>false</code>
  */
 public boolean isNumber() {
  return ttype == StreamTokenizer.TT_NUMBER;
 }

 /**
  * Checks if current token is a word
  * @return <code>true</code> if current token is a word, else <code>false</code>
  */
 public boolean isWord() {
  return ttype == StreamTokenizer.TT_WORD;
 }

 /**
  * Checks if current token is an end of line
  * @return <code>true</code> if current token is an end of line, else
  *         <code>false</code>
  */
 public boolean isEOL() {
  return ttype == StreamTokenizer.TT_EOL;
 }

 /**
  * Checks if end of file or end of stream is reached
  * @return <code>true</code> if current token is EOS else
  *         <code>false</code>
  */
 public boolean isEOS() {
  return ttype == StreamTokenizer.TT_EOF;
 }

 /**
  * Returns the numeric value from the current token provided it is a number token.
  * @throws StokenizerException if the token is not a number
  * @return The numeric value <code>double</code> 
  */
 public double getNumber() {
  if (isNumber()) {
   return nval;
  } else {
   throw new StokenizerException("Illegal call to getNumber. Token: " + toString());
  }
 }

 /**
  * Returns the word in the current token provided it is a word token.
  * @throws StokenizerException if the token is not a word
  * @return The word in the current token
  */
 public String getWord() {
  if (isWord()) {
   return sval;
  } else {
   throw new StokenizerException("Illegal call to getWord. Token: " + toString());
  }
 }

 /**
  * Returns the code for the current token. If the token is a number, a word or EOS the code is a negative
  * number, else its is the code of the current character. The initial value (before any call to nextToken())
  * is -4.
  * @return The token code
  */
 public int getChar() {
  return ttype;
 }

 /**
  * Returns the current token as a String
  * @return Current token as a String
  */
 public String getToken() {
  if (isWord()) {
   return getWord();
  } else if (isNumber()) {
   return "" + getNumber();
  } else if (getChar() == StreamTokenizer.TT_EOF) {
   return "*EOS*";
  } else if (getChar() == StreamTokenizer.TT_EOL) {
   return "*EOL*";
  } else if (getChar() < ' ') {
   return "*???*";
  } else {
   return "" + (char) getChar();
  }
 }

 /**
  * Return the previous token
  * @return the previous token as a String
  */
 public String getPreviousToken() {
   return previousToken;
 }
 
 /**
  * Returns a String representation of the current token including the token type
  * @return A String representation
  */
 public String toString() {
  if (isWord()) {
   return "Word[\"" + getWord() + "\"]";
  } else if (isNumber()) {
   return "Number[" + getNumber() + "]";
  } else if (isEOL()) {
   return "*EOL*";
  } else if (isEOS()) {
   return "*EOS*";
  } else if (getChar() < ' ') {
   return "Char[' '] (i e ascii " + ((int) getChar() + ")");
  } else {
   return "Char['" + (char) getChar() + "'] (i e ascii " + getChar()
     + ")";
  }
 }
}
