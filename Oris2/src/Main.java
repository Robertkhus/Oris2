import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "billyware123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb";

    public static void main(String[] args) throws Exception {

        // Подключаемся к базе данных
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();

        // Выполняем запрос к базе данных
//        ResultSet result = statement.executeQuery("select * from driver ");
//        while (result.next()) {
//            System.out.println(result.getInt("id") + " " + result.getString("name") + " " + result.getString("age"));
//        }

        // Считываем данные от пользователя
        String sqlUpdateUser = "SELECT * FROM driver WHERE CAST(age AS INTEGER) > ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sqlUpdateUser);
        preparedStatement2.setInt(1, 18);
        ResultSet result2 = preparedStatement2.executeQuery();
        while (result2.next()) {
            int id = result2.getInt("id");
            String name = result2.getString("name");
            String surname = result2.getString("surname");
            int age = result2.getInt("age");
            System.out.println("ID: " + id + ", Имя: " + name + ", Фамилия: " + surname + ", Возраст: " + age);
        }
        Scanner scanner = new Scanner(System.in);
        String sqlInsertUser = "insert into driver(name, surname, age) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        for (int i = 0; i < 6; i++) {
            System.out.println("Введите имя:");
            String firstName = scanner.nextLine();
            System.out.println("Введите фамилию:");
            String secondName = scanner.nextLine();
            System.out.println("Введите возраст:");
            String age = scanner.nextLine();




            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setString(3, age);
            preparedStatement.addBatch();
        }


        int[] affectedRows = preparedStatement.executeBatch();
        System.out.println("Было добавлено " + affectedRows.length + " строк");

        // Закрываем ресурсы
        result2.close();
        statement.close();
        preparedStatement.close();
        connection.close();
    }
}
