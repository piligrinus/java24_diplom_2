package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;

public class UserAssertions {

    @Step ("Проверка успешного создания пользователя")
    public String createSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK)
                .and()
                .extract().path("accessToken");

    }

    @Step ("Проверка неуспешного создания пользователя")
    public void createRegisteredUserUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("User already exists"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }

    @Step ("Проверка создания пользователя без передачи пароля или имени")
    public void createUserWithoutField(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);

    }

    @Step ("Проверка успешного логина пользователя")
    public void logInSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step ("Проверка неуспешного логина пользователя")
    public void logInUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step ("Проверка успешного изменения полей пользователя")
    public void changeUserFieldsSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step ("Проверка неуспешного изменения полей пользователя")
    public void changeUserFieldsUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

}
