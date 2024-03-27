package com.example.grpctask;

import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import com.example.grpctask.repository.BookEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "isbn",source = "isbn")
    @Mapping(target = "quantity",source = "quantity")
    BookResponse bookEntityToBookResponse(BookEntity bookEntity);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "isbn",source = "isbn")
    @Mapping(target = "quantity",source = "quantity")
    BookEntity bookRequestToBookEntity(BookRequest bookRequest);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "isbn",source = "isbn")
    @Mapping(target = "quantity",source = "quantity")
    BookRequest mapBookToBookRequest(Book book);
    @Mapping(target = "id", source = "id",qualifiedByName = "mapUUIDToString")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "isbn",source = "isbn")
    @Mapping(target = "quantity",source = "quantity")
    Book mapBookResponseToBook(BookResponse bookResponse);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "isbn",source = "isbn")
    @Mapping(target = "quantity",source = "quantity")
    BookRequest mapCreateBookReqToBookRequest(CreateBookRequest req);
    @Named("mapUUIDToString")
    default String mapUUIDToString(UUID uuid) {
        return uuid.toString();
    }


}