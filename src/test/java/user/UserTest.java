package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    User user;
   public String token;


    @After
    public void deleteUser(){
      if (token != null) {
      client.delete(token);
      }
    }


    @Step
    public void createRandomUser(){
        user = generator.genericRandom();
        ValidatableResponse creationResponse = client.create(user);
        token = check.createSuccessfully(creationResponse);
    }
    @Step
    public void createStaticUser(){
        user = generator.generic();
        ValidatableResponse creationResponse = client.create(user);
        token = check.createSuccessfully(creationResponse);
    }

    @Test
    public void createUserTest(){
        createRandomUser();
    }

    @Test
    public void createRegisteredUserTest(){
        createStaticUser();
        ValidatableResponse creationResponse = client.create(user);
        check.createRegisteredUserUnsuccessfully(creationResponse);

    }

    @Test
    public void createUserWithoutEmailTest(){
        user = generator.genericWithoutEmail();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @Test
    public void createUserWithoutPasswordTest(){
        user = generator.genericWithoutPassword();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @Test
    public void createWithoutNameTest(){
        user = generator.genericWithoutName();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutField(creationResponse);
    }

    @Test
    public void logInSuccessfullyTest(){
        createRandomUser();
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInSuccessfully(logInResponse);
    }

    @Test
    public void logInWithWrongEmail(){
        createRandomUser();
        user.setEmail("wrong@gmail.com");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnSuccessfully(logInResponse);
    }

    @Test
    public void logInWithWrongPassword(){
        createRandomUser();
        user.setPassword("wrong_pass");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnSuccessfully(logInResponse);
    }

    @Test
    public void changeUserFieldsSuccessfully(){
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFields(token,user);
        check.changeUserFieldsSuccessfully(changeUserFieldsResponse);

    }
    @Test
    public void changeUserFieldsUnSuccessfully() {
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFieldsWithoutAutorization(user);
        check.changeUserFieldsUnSuccessfully(changeUserFieldsResponse);
    }
}
