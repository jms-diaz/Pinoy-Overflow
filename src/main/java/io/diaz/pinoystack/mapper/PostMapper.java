package io.diaz.pinoystack.mapper;

import io.diaz.pinoystack.dto.PostRequest;
import io.diaz.pinoystack.dto.PostResponse;
import io.diaz.pinoystack.models.Post;
import io.diaz.pinoystack.models.Subforum;
import io.diaz.pinoystack.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subforum subforum, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapToDto(Post post);
}
