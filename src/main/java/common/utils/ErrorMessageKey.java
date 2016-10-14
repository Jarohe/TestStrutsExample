package common.utils;

public class ErrorMessageKey {
    public static class DeleteUser {
        public static String CAN_NOT_REMOVE = "error.can.not.remove";
        public static String NOT_FOUND_USER_ID = "error.not.found.user.id";
        public static String ID_NOT_NUMBER = "error.id.is.not.number";
    }

    public static class CreateUser {
        public static String CAN_NOT_BLANK = "errors.required";
        public static String DUBLICATE_LOGIN = "error.dublicate.user.login";
    }

    public static class EditUser {
        public static String NOT_FOUND_USER_ID = "error.not.found.user.id";
    }

    public static class Login {
        public static String INVALIDE_LOGIN_PASSWORD = "error.login.password";
    }

    public static class UpdateUser {
        public static String NOT_SEND_ID = "errors.not.id";
        public static String USER_NOT_UPDATE = "error.user.not.update";
        public static String DUBLICATE_LOGIN = "error.dublicate.user.login";
        public static String CAN_NOT_EDIT = "error.can.not.edit";

    }
}
