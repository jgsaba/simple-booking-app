package hostfully.test.booking_app.service;

import hostfully.test.booking_app.domain.dto.block.BlockDTO;
import hostfully.test.booking_app.domain.persistence.entities.Block;
import hostfully.test.booking_app.domain.persistence.entities.Property;
import hostfully.test.booking_app.domain.exceptions.entities.BlockNotFoundException;
import hostfully.test.booking_app.domain.exceptions.entities.PropertyNotFoundException;
import hostfully.test.booking_app.domain.persistence.repositories.BlockRepository;
import hostfully.test.booking_app.domain.persistence.repositories.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockService {

    private final BookingService bookingService;
    private final BlockRepository blockRepository;
    private final PropertyRepository propertyRepository;

    public BlockService(BookingService bookingService, BlockRepository blockRepository, PropertyRepository propertyRepository) {
        this.bookingService = bookingService;
        this.blockRepository = blockRepository;
        this.propertyRepository = propertyRepository;
    }

    @Transactional
    public BlockDTO createBlock(BlockDTO blockDTO){

        Property property = propertyRepository.findById(blockDTO.propertyId())
                .orElseThrow(PropertyNotFoundException::new);

        bookingService.cancelAllOverlappingBookings(blockDTO.propertyId(), blockDTO.startDate(), blockDTO.endDate());

        Block newBlock = blockRepository.save(new Block()
                .setProperty(property)
                .setStartDate(blockDTO.startDate())
                .setEndDate(blockDTO.endDate()));

        return BlockDTO.from(newBlock);
    }

    @Transactional
    public BlockDTO updateBlock(Long id, BlockDTO blockDTO){

        Block block = blockRepository.findById(id)
                .orElseThrow(BlockNotFoundException::new);

        Property property = propertyRepository.findById(blockDTO.propertyId())
                .orElseThrow(PropertyNotFoundException::new);

        bookingService.cancelAllOverlappingBookings(blockDTO.propertyId(), blockDTO.startDate(), blockDTO.endDate());

        Block updatedBlock = block.setStartDate(blockDTO.startDate())
                .setEndDate(blockDTO.endDate())
                .setProperty(property);

        return BlockDTO.from(blockRepository.save(updatedBlock));
    }

    public void deleteBlock(Long id){
        blockRepository.deleteById(id);
    }
}
