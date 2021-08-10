package com.cihan.kalah.util.mapper;

import com.cihan.kalah.model.dto.StartGameResponse;
import com.cihan.kalah.model.Game;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;

//@Mapper(componentModel = "spring")
public interface GameMapper {

 //   @Mapping(source = "url", target = "uri")
    StartGameResponse toStartGameResponse(Game game);
}
