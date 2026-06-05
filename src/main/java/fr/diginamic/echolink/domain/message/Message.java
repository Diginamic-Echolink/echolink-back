package fr.diginamic.echolink.domain.message;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.thread.Thread;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {

    /** Unique identifier of the Message. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Message's text */
    @Column(name="text", length = 10000)
    private String text;

    /** Thread to which this message belongs */
    @ManyToOne
    @JoinColumn(name="thread_id")
    private Thread thread;

    /** Profile that created the message */
    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;

    /** Constructor for: Message */
    public Message() {}

    /**
     * Constructor for: Message
     *
     * @param text
     */
    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Profile profile, Thread thread) {
        this.text = text;
        this.profile = profile;
        this.thread = thread;

    }

    /** @return toString */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", thread=" + thread +
                ", profile=" + profile +
                '}';
    }
}
