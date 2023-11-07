package order;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.UserTest;

public class OrderTest {

    Order order;

    private final OrderAssertions check = new OrderAssertions();
    private final OrderClient client = new OrderClient();
    private final OrderGenerator generator = new OrderGenerator();
    UserTest userTest = new UserTest();

    @After
    public void deleteUser() {
       userTest.deleteUser();
    }

    @Test
    public void createWithAutorizationAndValidateIngredientsTest(){
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAutorizationAndValidateIngredients(userTest.token,order);
        check.createOrderWithAuthorizationSuccessfully(creationResponse);
    }

    @Test
    public void createWithoutAutorizationAndValidateIngredientsTest(){
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithoutAutorization(order);
        check.createOrderWithAuthorizationSuccessfully(creationResponse);
    }
    @Test
    public void createWithAutorizationAndUnValidateIngredientsTest(){
        userTest.createRandomUser();
        order = generator.genericWithUnValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAutorizationAndUnValidateIngredients (userTest.token,order);
        check.createOrderWithAuthorizationUnSuccessfully(creationResponse);
    }
    @Test
    public void createWithAutorizationAndWithoutIngredientsTest(){
        userTest.createRandomUser();
        ValidatableResponse creationResponse = client.createWithAutorizationAndWithoutIngredients(userTest.token);
        check.createOrderWithoutIngredientsUnSuccessfully(creationResponse);
    }

    @Test
    public void getOrderWithAutorizationTest(){
        userTest.createRandomUser();
        ValidatableResponse creationResponse = client.getOrderWithoutAutorization();
        check.getOrderWithoutAutorization(creationResponse);
    }

}
