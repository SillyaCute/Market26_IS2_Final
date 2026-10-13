package exceptions;
public class NoGmailException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NoGmailException()
  {
    super();
  }
  /**This exception is triggered if the date of the product to sell is previous than today
  *@param s String of the exception
  */
  public NoGmailException(String s)
  {
    super(s);
  }
}