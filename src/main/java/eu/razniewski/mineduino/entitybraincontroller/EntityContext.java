package eu.razniewski.mineduino.entitybraincontroller;

public class EntityContext {
    private String contextTopic;

    public EntityContext(String contextTopic) {
        this.contextTopic = contextTopic;
    }

    public String getContextTopic() {
        return contextTopic;
    }

    public void setContextTopic(String contextTopic) {
        this.contextTopic = contextTopic;
    }
}
