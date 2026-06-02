package fr.diginamic.echolink.domain.section;

import fr.diginamic.echolink.domain.thread.Thread;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a discussion section within the EchoLink forum.
 * A section groups together threads related to a specific topic.
 */
@Getter
@Setter
@Entity
@Table(name = "section")
public class Section {

    /**
     * Unique identifier of the section.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Name displayed for the section.
     */
    @Column(name = "name")
    private String name;

    /**
     * Main topic discussed in this section.
     */
    @Column(name = "topic")
    private String topic;

    /**
     * Threads belonging to this section.
     */
    @OneToMany(mappedBy = "section")
    private List<Thread> threads = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public Section() {}

    /**
     * Creates a discussion section.
     *
     * @param name  name of the section
     * @param topic subject covered by the section
     */
    public Section(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                ", threads=" + threads +
                '}';
    }
}
