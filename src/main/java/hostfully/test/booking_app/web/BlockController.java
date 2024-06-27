package hostfully.test.booking_app.web;

import hostfully.test.booking_app.domain.dto.block.BlockDTO;
import hostfully.test.booking_app.service.BlockService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blocks")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockDTO createBooking(@RequestBody @Validated BlockDTO blockDTO){
        return blockService.createBlock(blockDTO);
    }

    @PutMapping("/{id}")
    public BlockDTO updateBlock(@PathVariable Long id, @RequestBody @Validated BlockDTO blockDTO){
        return blockService.updateBlock(id, blockDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlock(@PathVariable Long id){
        blockService.deleteBlock(id);
    }
}
