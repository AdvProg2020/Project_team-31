package Model;

public abstract class Request {
    private Manager manager;

    public Request(Manager manager) {
        this.manager = manager;
    }
}
