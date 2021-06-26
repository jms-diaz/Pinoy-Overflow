package io.diaz.pinoystack.mapper;

import io.diaz.pinoystack.dto.SubforumDto;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.Subforum;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubforumMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subforum.getPosts()))")
    SubforumDto mapSubforumToDto(Subforum subforum);


    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subforum mapDtoToSubforum(SubforumDto subforumDto);
}
