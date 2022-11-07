package com.backend.uberclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipient_id")
    private User recipient;

    @Column
    private LocalDateTime date;

    @Column
    private String text;

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column
    private boolean read;

}
