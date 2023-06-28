import java.util.Scanner;

public class Exam_Advance_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số ISBN 10 số: ");
        String isbn = scanner.nextLine();

        scanner.close();

        System.out.println(isbn.length() == 10 && checkISBN(isbn) ? "Số ISBN hợp lệ." : "Số ISBN không hợp lệ.");
    }

    private static boolean checkISBN(String isbn) {
        int sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            char digitChar = isbn.charAt(i);
            if (!Character.isDigit(digitChar)) {
                return false;
            }
            int digit = Character.getNumericValue(digitChar);
            sum += (i + 1) * digit;
        }
        return (sum % 11 == 0);
    }
}
