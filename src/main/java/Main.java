import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        String pathFile = "src/test/resources/movementList.csv";
        Movements movements = new Movements(pathFile);
        //movements.printMovements();
        System.out.println("Общая сумма дохода : " + movements.getIncomeSum());
        System.out.println("Общая сумма расхода : " + movements.getExpenseSum());

        movements.getDetailedExpenses();
    }
}
