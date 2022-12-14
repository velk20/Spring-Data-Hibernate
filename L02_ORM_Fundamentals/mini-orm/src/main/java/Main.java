import entities.User;
import orm.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        // Create "custom-orm" schema in DB before starting the MAIN
        createConnection("root", "root", "custom-orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

        User user = new User("pesho", 20, LocalDate.now(), LocalDate.now().minusDays(300));

        user.setId(2);
        user.setUsername("Angel_M");

//        userEntityManager.delete(user);
//
//        userEntityManager.doAlter(User.class);
        userEntityManager.persist(user);


        connection.close();
    }
}
