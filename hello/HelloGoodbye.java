/* *****************************************************************************
 *  Name:              Roman Cano
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


public class HelloGoodbye {
    public static void main(String[] args) {

        if (args.length != 2) {
            return;
        }
        String nameOne = args[0];
        String nameTwo = args[1];
        System.out.println("Hello " + nameOne + " and " + nameTwo + ".");
        System.out.println("Goodbye " + nameTwo + " and " + nameOne + ".");
    }

}
