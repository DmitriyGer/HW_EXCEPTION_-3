package Home003;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные пользователя (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String userData = scanner.nextLine();

        try {
            String[] userDataArray = userData.split(" ");
            if (userDataArray.length != 6) {
                throw new InvalidDataFormatException("Неверное количество данных. Введите Фамилию Имя Отчество дату_рождения номер_телефона пол");
            }

            String surname = userDataArray[0];
            String name = userDataArray[1];
            String patronymic = userDataArray[2];
            String birthDate = userDataArray[3];
            long phoneNumber = Long.parseLong(userDataArray[4]);
            char gender = userDataArray[5].charAt(0);

            if (!birthDate.matches("\\d{2}.\\d{2}.\\d{4}") || !(gender == 'f' || gender == 'm')) {
                throw new InvalidDataFormatException("Неверный формат данных. Дата рождения должна быть в формате dd.mm.yyyy, номер телефона - числа введеные без пробела, а пол - 'f' или 'm'.");
            }

            if (!containsOnlyLatinCharacters(surname) || !containsOnlyLatinCharacters(name) || !containsOnlyLatinCharacters(patronymic)) {
                throw new InvalidDataFormatException("Введите Фамилию, Имя и Отчество только на латинице.");
            }

            File file = new File(surname + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            String userDataLine = String.format("%s %s %s %s %d %c", surname, name, patronymic, birthDate, phoneNumber, gender);
            writer.write(userDataLine);
            writer.newLine();
            writer.close();

            System.out.println("Данные пользователя успешно сохранены в файл '" + surname + ".txt'.");

        } catch (InvalidDataFormatException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Неверный формат номера телефона.");
        } catch (IOException e) {
            System.err.println("Произошла ошибка при записи в файл.");
            e.printStackTrace();
        }
    }

    private static boolean containsOnlyLatinCharacters(String str) {
        return str.matches("[a-zA-Z]+");
    }
}