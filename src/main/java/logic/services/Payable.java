package logic.services;

public interface Payable {

    public boolean isBilled();
    public void bill(Long cardNumber, String nameOnCard);

}
