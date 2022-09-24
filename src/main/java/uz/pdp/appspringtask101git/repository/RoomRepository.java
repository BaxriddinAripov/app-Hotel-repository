package uz.pdp.appspringtask101git.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringtask101git.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);

    Page<Room> findRoomsByHotelId(Integer hotel_id, Pageable pageable);
}
