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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="text", length = 10000)
    private String text;

    @Column(name="like_count")
    private int likeCount;

    @Column(name="dislike_count")
    private int dislikeCount;

    @ManyToOne
    @JoinColumn(name="thread_id")
    private Thread thread;

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;

    public Message() {}

    public Message(String text, int likeCount, int dislikeCount) {
        this.text = text;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

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
