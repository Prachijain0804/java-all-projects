import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            Calculator obj =
                (Calculator) Naming.lookup(
                    "rmi://localhost/Calculator");
int addResult = obj.add(10,20);
int subtractResult =obj.subtract(20,10);

            System.out.println("Addition = " + addResult);
            System.out.println("Subtraction = " + subtractResult);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}