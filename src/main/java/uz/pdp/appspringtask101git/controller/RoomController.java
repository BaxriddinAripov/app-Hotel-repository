package uz.pdp.appspringtask101git.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringtask101git.entity.Hotel;
import uz.pdp.appspringtask101git.entity.Room;
import uz.pdp.appspringtask101git.payload.RoomDto;
import uz.pdp.appspringtask101git.repository.HotelRepository;
import uz.pdp.appspringtask101git.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    // CREAT
    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        boolean exists = roomRepository.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId());
        if (exists) {
            return "Room already exist";
        }
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "Hotel not found";
        }
        Hotel hotel = optionalHotel.get();
        room.setHotel(hotel);
        roomRepository.save(room);
        return "Room added";
    }

    //READ
    @GetMapping
    public Page<Room> getAllRooms(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Room> allRooms = roomRepository.findAll(pageable);
        return allRooms;
    }

    //READ
    @GetMapping("/forHotel/{hotelId}")
    public Page<Room> getRoomListForHotel(@RequestParam int page, @PathVariable Integer hotelId) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomsPage = roomRepository.findRoomsByHotelId(hotelId, pageable);
        return roomsPage;
    }

    // DELETE
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        roomRepository.deleteById(id);
        return "Room deleted";
    }

    @PutMapping("{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()){
            return "Room not found";
        }
        Room room = optionalRoom.get();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()){
            return "Hotel not found";
        }
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room edited";
    }
}
