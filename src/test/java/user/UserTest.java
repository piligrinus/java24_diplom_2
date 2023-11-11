package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    public String token;
    User user;

    @After
    public void deleteUser() {
        if (token != null) {
            client.delete(token);
        }
    }


    @Step("Создание случайного пользователя")
    public void createRandomUser() {
        user = generator.genericRandom();
        ValidatableResponse creationResponse = client.create(user);
        token = check.createSuccessfully(creationResponse);
    }

    @Step("Создание заданного пользователя")
    public void createStaticUser() {
        user = generator.generic();
        ValidatableResponse creationResponse = client.create(user);
        token = check.createSuccessfully(creationResponse);
    }

    @DisplayName("Успешное создание случайного пользователя")
    @Test
    public void createUserTest() {
        createRandomUser();
    }

    @DisplayName("Неуспешное создание существующего пользователя")
    @Test
    public void createRegisteredUserTest() {
        createStaticUser();
        ValidatableResponse creationResponse = client.create(user);
        check.createRegisteredUserUnsuccessfully(creationResponse);

    }

    @DisplayName("Неуспешное создание пользователя без email")
    @Test
    public void createUserWithoutEmailTest() {
        user = generator.genericWithoutEmail();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @DisplayName("Неуспешное создание пользователя без пароля")
    @Test
    public void createUserWithoutPasswordTest() {
        user = generator.genericWithoutPassword();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @DisplayName("Неуспешное создание пользователя без имени")
    @Test
    public void createWithoutNameTest() {
        user = generator.genericWithoutName();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @DisplayName("Успешный логин пользователя")
    @Test
    public void logInSuccessfullyTest() {
        createRandomUser();
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInSuccessfully(logInResponse);
    }

    @DisplayName("Неуспешный логин пользователя с неверным email")
    @Test
    public void logInWithWrongEmail() {
        createRandomUser();
        user.setEmail("wrong@gmail.com");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnSuccessfully(logInResponse);
    }

    @DisplayName("Неуспешный логин пользователя с неверным паролем")
    @Test
    public void logInWithWrongPassword() {
        createRandomUser();
        user.setPassword("wrong_pass");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnSuccessfully(logInResponse);
    }

    @DisplayName("Успешное изменение полей пользователя")
    @Test
    public void changeUserFieldsSuccessfully() {
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFields(token, user);
        check.changeUserFieldsSuccessfully(changeUserFieldsResponse);

    }

    @DisplayName("Неуспешное изменение полей пользователя без авторизации")
    @Test
    public void changeUserFieldsUnSuccessfully() {
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFieldsWithoutAutorization(user);
        check.changeUserFieldsUnSuccessfully(changeUserFieldsResponse);
    }
}
