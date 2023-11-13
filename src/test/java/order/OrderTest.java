package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.UserTest;

public class OrderTest {

    private final OrderAssertions check = new OrderAssertions();
    private final OrderClient client = new OrderClient();
    private final OrderGenerator generator = new OrderGenerator();
    Order order;
    UserTest userTest = new UserTest();

    @After
    public void deleteUser() {
        userTest.deleteUser();
    }

    @DisplayName("Создание заказа с авторизацией и валидными ингредиентами")
    @Test
    public void createWithAutorizationAndValidateIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAutorizationAndValidateIngredients(userTest.token, order);
        check.createOrderWithAuthorizationSuccessfully(creationResponse);
    }

    @DisplayName("Создание заказа без авторизации, с валидными ингредиентами")
    @Test
    public void createWithoutAutorizationAndValidateIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithoutAutorization(order);
        check.createOrderWithAuthorizationSuccessfully(creationResponse);
    }

    @DisplayName("Создание заказа с авторизацией и с невалидными ингредиентами")
    @Test
    public void createWithAutorizationAndUnValidateIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithUnValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAutorizationAndUnValidateIngredients(userTest.token, order);
        check.createOrderWithAuthorizationUnSuccessfully(creationResponse);
    }

    @DisplayName("Создание заказа с авторизацией и без ингредиентов")
    @Test
    public void createWithAutorizationAndWithoutIngredientsTest() {
        userTest.createRandomUser();
        ValidatableResponse creationResponse = client.createWithAutorizationAndWithoutIngredients(userTest.token);
        check.createOrderWithoutIngredientsUnSuccessfully(creationResponse);
    }

    @DisplayName("Получение заказа")
    @Test
    public void getOrderWithAutorizationTest() {
        userTest.createRandomUser();
        ValidatableResponse creationResponse = client.getOrderWithoutAutorization();
        check.getOrderWithoutAutorization(creationResponse);
    }

}
