package uz.pdp.appspringtask101git.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringtask101git.entity.Hotel;
import uz.pdp.appspringtask101git.repository.HotelRepository;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    // READ
    @GetMapping
    public Page<Hotel> hotelPage(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        return hotelRepository.findAll(pageable);
    }

    // READ
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }

    // CREATE
    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists){
            return "Hotel already exist";
        }
        hotelRepository.save(hotel);
        return "Hotel added";
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            hotelRepository.delete(hotel);
            return "Hotel deleted";
        }
        return "Hotel not found";
    }

    // UPDATE
    @PutMapping("/{id}")
    public String editHotelById(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()){
            return "Hotel not found";
        }
        Hotel hotel1 = optionalHotel.get();
        hotel1.setName(hotel.getName());
        hotelRepository.save(hotel1);
        return "Hotel edited";
    }
}
