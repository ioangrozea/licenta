package hello.exeption;

public enum ExceptionCode {
    GET_DOCUMENT_FAIL(10, "Document can not be loaded"),
    NOT_A_VALID_WEBSITE(11, "Website name is not valid");

    int id;
    String message;

    ExceptionCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
