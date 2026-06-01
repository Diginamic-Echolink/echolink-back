package fr.diginamic.echolink.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a discussion section within the EchoLink forum.
 * A section groups together threads related to a specific topic.
 */
@Entity
@Getter
@Setter
public class Section {

    /**
     * Unique identifier of the section.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Name displayed for the section.
     */
    private String name;

    /**
     * Main topic discussed in this section.
     */
    private String topic;

    /**
     * Threads belonging to this section.
     */
    @OneToMany(mappedBy = "section")
    private Set<Thread> threads;

    /**
     * Default constructor required by JPA.
     */
    public Section() {}

    /**
     * Creates a discussion section.
     *
     * @param id unique identifier of the section
     * @param name name of the section
     * @param topic subject covered by the section
     */
    public Section(
            UUID id,
            String name,
            String topic) {
        this.id = id;
        this.name = name;
        this.topic = topic;
    }

    /**
     * Returns a string representation of the section.
     *
     * @return a string containing the section information
     */
    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}