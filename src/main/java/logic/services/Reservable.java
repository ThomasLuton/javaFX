package logic.services;

public interface Reservable {
    public void book();
    public boolean isBookable();
    public void cancel();
}
