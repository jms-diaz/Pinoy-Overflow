package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.SubforumDto;
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

    @Transactional
    public SubforumDto save(SubforumDto subforumDto){
        Subforum save = subforumRepo.save(mapSubforumDto(subforumDto));
        subforumDto.setId(save.getId());
        return subforumDto;

    }
    
    @Transactional(readOnly = true)
    public List<SubforumDto> getAll() {
        return subforumRepo.findAll().stream().map(this::mapToDto).collect(toList());
    }

    private SubforumDto mapToDto(Subforum subforum) {
        return SubforumDto.builder()
                .name(subforum.getName())
                .id(subforum.getId())
                .numberOfPosts(subforum.getPosts().size())
                .build();
    }

    private Subforum mapSubforumDto(SubforumDto subforumDto) {
        return Subforum.builder().name(subforumDto.getName())
                .description(subforumDto.getDescription())
                .build();
    }

}
