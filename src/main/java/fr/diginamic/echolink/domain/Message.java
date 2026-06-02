package fr.diginamic.echolink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="text", length = 10000)
    private String text;

    @Column(name="likes_count")
    private int likeCnt;

    @Column(name="dislikes_count")
    private int dislikeCnt;

    @ManyToOne
    @JoinColumn(name="thread_id")
    private Thread thread;

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;

    public Message() {
    }

    public Message(UUID id, String text, int likeCnt, int dislikeCnt, Thread thread, Profile profile) {
        this.id = id;
        this.text = text;
        this.likeCnt = likeCnt;
        this.dislikeCnt = dislikeCnt;
        this.thread = thread;
        this.profile = profile;
    }

    public Message(int dislikeCnt, int likeCnt, String text, UUID id) {
        this.dislikeCnt = dislikeCnt;
        this.likeCnt = likeCnt;
        this.text = text;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", likeCnt=" + likeCnt +
                ", dislikeCnt=" + dislikeCnt +
                ", thread=" + thread +
                ", profile=" + profile +
                '}';
    }
}
