package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.SubforumDto;
import io.diaz.pinoystack.exceptions.PinoyStackException;
import io.diaz.pinoystack.mapper.SubforumMapper;
import io.diaz.pinoystack.models.Subforum;
import io.diaz.pinoystack.repo.SubforumRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class SubforumService {

    private final SubforumRepo subforumRepo;
    private final SubforumMapper subforumMapper;

    @Transactional
    public SubforumDto save(SubforumDto subforumDto){
        Subforum save = subforumRepo.save(subforumMapper.mapDtoToSubforum(subforumDto));
        subforumDto.setId(save.getId());
        return subforumDto;

    }
    
    @Transactional(readOnly = true)
    public List<SubforumDto> getAll() {
        return subforumRepo.findAll()
                .stream()
                .map(subforumMapper::mapSubforumToDto)
                .collect(toList());
    }

    public SubforumDto getSubforum(Long id) {
        Subforum subforum = subforumRepo.findById(id)
                .orElseThrow(() -> new PinoyStackException("No forum found with id " + id));
        return subforumMapper.mapSubforumToDto(subforum);
    }
}
