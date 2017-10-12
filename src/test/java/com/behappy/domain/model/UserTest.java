package com.behappy.domain.model;

import com.behappy.api.user.request.UserRequest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Bartosz on 23.11.2016.
 */
//public class UserTest {
//
//    public static final String TEST_EMAIL = "e@mail.test";
//    public static final String TEST_PASSWORD = "password";
//
//    public static final User getTestUserInstance() {
//        return new User(TEST_EMAIL, TEST_PASSWORD);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void failsIfEmailNotSet() throws Exception {
//        new User("", "password");
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void failsIfEmailIsNull() throws Exception {
//        new User(null, "password");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void failsIfEmailHasWrongFormat() throws Exception {
//        new User("email", TEST_PASSWORD);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void failsIfPasswordNotSet() throws Exception {
//        new User(TEST_EMAIL, "");
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void failsIfPasswordIsNull() throws Exception {
//        new User(TEST_EMAIL, null);
//    }
//
//    @Test
//    public void createsProperlyWithValidData() throws Exception {
//        User user = getTestUserInstance();
//        assertThat(user.getMail(), Matchers.equalTo(TEST_EMAIL));
//        assertThat(user.getPassword(), Matchers.equalTo(TEST_PASSWORD));
//    }
//
//    @Test(expected = Exception.class)
//    public void setEmailUndergoesValidation() throws Exception {
//        User user = getTestUserInstance();
//        user.setEmail("email");
//    }
//}











