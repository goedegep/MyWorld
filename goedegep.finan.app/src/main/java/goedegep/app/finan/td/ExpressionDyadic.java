package goedegep.app.finan.td;

import java.awt.Component;
import java.util.logging.Logger;


public class ExpressionDyadic extends Expression {
  private static final Logger         LOGGER = Logger.getLogger(ExpressionDyadic.class.getName());

  private OperatorDyadic  operator;
  private Object          operandLeft;
  private Object          operandRight;

  public ExpressionDyadic(Object operandLeft, OperatorDyadic operator,
      Object operandRight) {
    super();
    this.operator = operator;
    this.operandLeft = operandLeft;
    this.operandRight = operandRight;
  }

  @Override
  public Object evaluate(TransactionDialogComponentList transactionComponents) {
    LOGGER.info("=>");
    Boolean returnValue;
    
    switch (operator) {
    case EQUALS:
      if (operandLeft == null || operandRight == null) {
        return Boolean.FALSE;
      }
      if (operandLeft instanceof CD) {
        LOGGER.info("instanceof CD");
        CD cd = (CD) operandLeft;
//        Object objectLeft = cd.getValue(transactionComponents.get(cd.getName()));
        DC<? extends Component> componentLeft = transactionComponents.get(cd.getName());
        if (componentLeft == null) {
          LOGGER.info("<= FALSE (componentLeft == null)");
          return Boolean.FALSE;
        }
        Object objectLeft = componentLeft.getValue();
        if (objectLeft == null) {
          LOGGER.info("<= FALSE (objectLeft == null)");
          return Boolean.FALSE;
        }
        if (objectLeft.equals(operandRight)) {
          LOGGER.info("<= TRUE");
          return Boolean.TRUE;
        } else {
          LOGGER.info("<= FALSE");
          returnValue = Boolean.FALSE;
          return returnValue;
        }
      }
    }
    return Boolean.FALSE;
  }
}
