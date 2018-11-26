import java.util.Date;

public final class Apple extends Fruit {

  public Apple(String colour, Date bestBeforeDate) {
    super(colour, bestBeforeDate);
  }

  public void ripen() {
    //empty implementation of abstract method
  }

  /**
  * The Apple subclass does not support clone.
  */
  @Override public final Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }
} 
