package ru.nsu.ccfit.petrov.dailyhelper.models.daos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    private String token;

    @OneToOne(cascade = CascadeType.REFRESH,
              fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
                nullable = false,
                unique = true)
    private User user;

    @Column(nullable = false)
    private Date expiredTime;
}
