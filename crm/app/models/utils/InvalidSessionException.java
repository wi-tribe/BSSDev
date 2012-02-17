package models.utils;

public class InvalidSessionException extends Exception
{
  public InvalidSessionException(String msg)
  {
    super(msg);
  }

  public InvalidSessionException()
  {
      
  }
}