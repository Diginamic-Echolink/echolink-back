package fr.diginamic.echolink.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a discussion thread within a forum section.
 * A thread is created by a user profile and can contain multiple messages.
 */
@Entity
@Getter
@Setter
public class Thread {

    /**
     * Unique identifier of the thread.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Title of the discussion thread.
     * Limited to 75 characters.
     */
    @Column(length = 75)
    private String title;

    /**
     * Description or subject of the discussion.
     * Limited to 500 characters.
     */
    @Column(length = 500)
    private String subject;

    /**
     * Date and time when the thread was created.
     */
    private LocalDateTime createdAt;

    /**
     * Number of likes received by the thread.
     */
    private int likesCnt;

    /**
     * Number of dislikes received by the thread.
     */
    private int dislikesCnt;

    /**
     * Forum section to which this thread belongs.
     */
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    /**
     * User profile that created the thread.
     */
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    /**
     * Messages posted within this thread.
     */
    @OneToMany(mappedBy = "thread")
    private Set<Message> messages;

    /**
     * Default constructor required by JPA.
     */
    public Thread() {}

    /**
     * Creates a discussion thread.
     *
     * @param id unique identifier of the thread
     * @param title title of the thread
     * @param subject subject of the discussion
     * @param createdAt date and time when the thread was created
     * @param likesCnt number of likes received by the thread
     * @param dislikesCnt number of dislikes received by the thread
     */
    public Thread(
            UUID id,
            String title,
            String subject,
            LocalDateTime createdAt,
            int likesCnt,
            int dislikesCnt) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.createdAt = createdAt;
        this.likesCnt = likesCnt;
        this.dislikesCnt = dislikesCnt;
    }

    /**
     * Returns a string representation of the thread.
     *
     * @return a string containing the thread information
     */
    @Override
    public String toString() {
        return "Thread{" +
                "dislikesCnt=" + dislikesCnt +
                ", likesCnt=" + likesCnt +
                ", createdAt=" + createdAt +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}