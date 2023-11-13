package order;

import base.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {

    public static final String INGREDIENTS = "/ingredients";
    public static final String ORDERS = "/orders";

    @Step ("Создание заказа с авторизацией и валидными ингредиентами")
    public ValidatableResponse createWithAutorizationAndValidateIngredients(String token, Order order) {
        return getRequestSpecification()
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();

    }

    @Step ("Создание заказа без авторизации")
    public ValidatableResponse createWithoutAutorization(Order order) {
        return getRequestSpecification()
                .body(order)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();

    }

    @Step ("Создание заказа с авторизацией и невалидными ингредиентами")
    public ValidatableResponse createWithAutorizationAndUnValidateIngredients(String token, Order order) {
        return getRequestSpecification()
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();

    }

    @Step ("Создание заказа с авторизацией и без ингредиентов")
    public ValidatableResponse createWithAutorizationAndWithoutIngredients(String token) {
        return getRequestSpecification()
                .header("Authorization", token)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();

    }

    @Step ("Получение заказа с авторизацией")
    public ValidatableResponse getOrderWithAutorization(String token) {
        return getRequestSpecification()
                .header("Authorization", token)
                .when()
                .get(ORDERS)
                .then()
                .log()
                .all();

    }

    @Step ("Получение заказа без авторизации")
    public ValidatableResponse getOrderWithoutAutorization() {
        return getRequestSpecification()
                .when()
                .get(ORDERS)
                .then()
                .log()
                .all();

    }

}
