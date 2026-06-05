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

/**
 * Entity representing a message posted by a user inside a thread.
 * <p>
 * A message belongs to a {@link Thread} and is authored by a {@link Profile}.
 * It contains the textual content written by the user.
 */
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
     * Constructs a new Message.
     *
     * @param text content of the message
     * @param profile author of the message
     * @param thread thread in which the message is posted
     */
    public Message(String text, Profile profile, Thread thread) {
        this.text = text;
        this.profile = profile;
        this.thread = thread;
    }

    /**
     * Returns a string representation of the Message entity.
     *
     * @return string representation containing id, text, thread and profile
     */
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
