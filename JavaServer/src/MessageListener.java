public interface MessageListener {
    public void onMessage(String fromLogin, String msgBody);
    public void onGroupMessage(String groupName, String fromLogin, String msgBody);
}
