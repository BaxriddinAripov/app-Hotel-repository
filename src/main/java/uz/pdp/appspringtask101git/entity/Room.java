package uz.pdp.appspringtask101git.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "hotel_id"}))
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Double size;

    @ManyToOne
    private Hotel hotel;
}
