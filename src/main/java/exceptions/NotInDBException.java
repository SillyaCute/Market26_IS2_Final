package exceptions;
public class NotInDBException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NotInDBException()
  {
    super();
  }
  /**This exception is triggered if the date of the product to sell is previous than today
  *@param s String of the exception
  */
  public NotInDBException(String s)
  {
    super(s);
  }
}