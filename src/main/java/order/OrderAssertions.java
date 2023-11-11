package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;

public class OrderAssertions {

    @Step ("Проверка, что заказ создан при условии успешной авторизации")
    public void createOrderWithAuthorizationSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }


    @Step ("Проверка, что заказ не создан без авторизации")
    public void createOrderWithAuthorizationUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step ("Проверка, что заказ не создан без ингредиентов")
    public void createOrderWithoutIngredientsUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step ("Проверка, что заказ не получен без авторизации")
    public void getOrderWithoutAutorization(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

}
