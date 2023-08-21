package ru.nsu.ccfit.petrov.dailyhelperapi.models.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_tokens")
@Data
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