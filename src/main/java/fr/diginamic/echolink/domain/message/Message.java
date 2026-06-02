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

    /** Message's like count */
    @Column(name="like_count")
    private int likeCount;

    /** Message's dislike count */
    @Column(name="dislike_count")
    private int dislikeCount;

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
     * @param likeCount
     * @param dislikeCount
     */
    public Message(String text, int likeCount, int dislikeCount) {
        this.text = text;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    /** @return toString */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", thread=" + thread +
                ", profile=" + profile +
                '}';
    }
}
