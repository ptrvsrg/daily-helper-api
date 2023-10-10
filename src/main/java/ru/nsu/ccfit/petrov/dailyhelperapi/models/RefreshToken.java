package ru.nsu.ccfit.petrov.dailyhelperapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;

import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Long id;

    @Column(nullable = false,
            unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private Date expiredTime;
}
