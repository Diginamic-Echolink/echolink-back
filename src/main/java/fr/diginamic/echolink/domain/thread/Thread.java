package fr.diginamic.echolink.domain.thread;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.section.Section;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a discussion thread within a forum section.
 * A thread is created by a user profile and can contain multiple messages.
 */
@Getter
@Setter
@Entity
@Table(name = "thread")
public class Thread {

    /**
     * Unique identifier of the thread.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Title of the discussion thread.
     * Limited to 75 characters.
     */
    @Column(name = "title", length = 75)
    private String title;

    /**
     * Description or subject of the discussion.
     * Limited to 500 characters.
     */
    @Column(name = "subject", length = 500)
    private String subject;

    /**
     * Date and time when the thread was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

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
    private List<Message> messages = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public Thread() {}

    /**
     * Creates a discussion thread.
     *
     * @param title        title of the thread
     * @param subject      subject of the discussion
     * @param createdAt    date and time when the thread was created
     * @param section      section of the thread
     * @param profile      profile owner
     */
    public Thread(String title, String subject, LocalDateTime createdAt, Section section, Profile profile) {
        this.title = title;
        this.subject = subject;
        this.createdAt = createdAt;
        this.section = section;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", createdAt=" + createdAt +
                ", section=" + section +
                ", profile=" + profile +
                ", messages=" + messages +
                '}';
    }
}
