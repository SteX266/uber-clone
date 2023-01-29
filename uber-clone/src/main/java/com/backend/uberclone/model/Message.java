package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message implements Comparable<Message>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipient_id")
    private User recipient;

    @Column
    private String text;

    @Column
    private LocalDateTime date;

    @Column
    private boolean read;

    @Override
    public int compareTo(@NotNull Message m) {
        return getDate().compareTo(m.getDate());
    }
}
