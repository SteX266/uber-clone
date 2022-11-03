package com.backend.uberclone.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActivePeriod {
    private LocalDateTime start;
    private LocalDateTime end;
}
