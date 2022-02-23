package goedegep.app.finan.td;


public class ExpressionMonadic extends Expression {
  private OperatorMonadic operator;
  private Object          operand;
  
  public ExpressionMonadic(OperatorMonadic operator, Object operand) {
    this.operator = operator;
    this.operand = operand;
  }
  
  @Override
  public Object evaluate(TransactionDialogComponentList transactionComponents) {
    switch (operator) {
    case EXISTS:
      if (!(operand instanceof CD)) {
        throw new IllegalArgumentException("Only ComponentDescriptor supported as operand for EXISTS");
      }
      String componentName = ((CD) operand).getName();

      if (transactionComponents.get(componentName) != null) {
        return true;
      } else {
        return false;
      }
//      for (Component component: transactionComponents) {
//        if (component.getName().equals(componentName)) {
//          return true;
//        }
//      }
//      return true;
    }

    return false;
  }

}
