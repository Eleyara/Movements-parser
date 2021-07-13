import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

public class Movements {

    private final String pathMovementCsv;
    private static String dateFormat = "dd.MM.yyyy";
    ArrayList<Movement> movements;



    public Movements(String pathMovementCsv){
        this.pathMovementCsv = pathMovementCsv;
        movements = loadMovementsFromFile(pathMovementCsv);
    }

    public double getExpenseSum() {

        double sum = 0.;

        for(Movement movement : movements){
            sum += movement.getExpense();
        }

        return sum;
    }

    public double getIncomeSum() {
        double sum = 0.;

        for(Movement movement : movements){
            sum += movement.getIncome();
        }

        return sum;
    }

    public void getDetailedExpenses(){
        TreeMap<String, Double> expenseSums2Purposes = new TreeMap<>();
        for(Movement movement : movements){
            double expense = movement.getExpense();
            String purpose = movement.getMovementDescription().getPurpose();
            if(expense != 0){
                if(expenseSums2Purposes.containsKey(purpose)){
                    expense += expenseSums2Purposes.get(purpose);
                }
                expenseSums2Purposes.put(purpose, expense);
            }
        }
        System.out.println("Детальные расходы:");
        for (String key : expenseSums2Purposes.keySet()){
            System.out.println("\t" + key + "\t" + expenseSums2Purposes.get(key));
        }
    }

    public ArrayList<Movement> loadMovementsFromFile(String filePath){
        ArrayList<Movement> movements = new ArrayList<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            for(;;)
            {
                line = reader.readLine();
                if(line == null){
                    break;
                }
                if (line.contains("\"")){
                    line = correctSeparatorOnOtherSymbol(line, ",", ".");
                }
                String[] lines = line.split(",");
                if(lines[0].equals("Тип счёта") || lines[0].equals("Тип счета")){
                    continue;
                }
                if(lines.length != 8) {
                    System.out.print("Wrong line format: " + line);
                    continue;
                }
                movements.add(new Movement(
                        lines[0],
                        lines[1],
                        lines[2],
                        (new SimpleDateFormat(dateFormat)).parse(lines[3]),
                        lines[4],
                        lines[5],
                        Double.parseDouble(lines[6].replaceAll("\"", "").replaceAll(",", ".")),
                        Double.parseDouble(lines[7].replaceAll("\"", "").replaceAll(",", "."))
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return movements;
    }

    public static String correctSeparatorOnOtherSymbol(String line, String separator, String value){

        boolean lineChecked = false;
        StringBuilder builder = new StringBuilder();

        while (!lineChecked) {
            if (!lineContainsTwoQuotationMarks(line)) {
                builder.append(line);
                break;
            }
            int firstQuotationMarkIndex = line.indexOf("\"");
            int secondQuotationMarkIndex = firstQuotationMarkIndex + 1 + line.substring(firstQuotationMarkIndex + 1).indexOf("\"");
            String helper = line.substring(firstQuotationMarkIndex, secondQuotationMarkIndex);

            if(helper.contains(separator) && helper.replaceAll("[^a-zA-Z]", "").equals("")){
                builder.append(line.substring(0, firstQuotationMarkIndex));
                builder.append(helper.replaceAll(separator, value));
            }
            else{
                builder.append(line.substring(0, secondQuotationMarkIndex));
            }
            if(secondQuotationMarkIndex == line.length()){
                lineChecked = true;
            }
            else {
                line = line.substring(secondQuotationMarkIndex + 1);
            }
        }

        return builder.toString();
    }

    public static boolean lineContainsTwoQuotationMarks(String line){
        return line.contains("\"") && (line.indexOf("\"") != line.lastIndexOf("\""));
    }

    public void printMovements(){
        for (Movement movement : movements){
            System.out.println(movement.ToString());
        }
    }
}
