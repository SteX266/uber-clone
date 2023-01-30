package com.backend.uberclone.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="notes")
        private User user;

        @Column
        private String text;

        @Column
        private LocalDateTime date;


}
