package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public User genericRandom() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@gmail.com", RandomStringUtils.randomAlphanumeric(4, 10), RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User generic() {
        return new User("test007@gmail.com", "QWer13!", "test007");
    }

    public User genericWithoutEmail() {
        return new User(null, RandomStringUtils.randomAlphanumeric(4, 10), RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User genericWithoutPassword() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@gmail.com", null, RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User genericWithoutName() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@gmail.com", RandomStringUtils.randomAlphanumeric(4, 10), null);
    }
}