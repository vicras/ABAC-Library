package com.vicras.abaclib.use.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.mapstruct.ReportingPolicy.ERROR;

import com.vicras.abaclib.use.dto.DocumentDTO;
import com.vicras.abaclib.use.model.Document;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class DocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Document toEntity(DocumentDTO docs);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "creatorId", source = "doc.creator.id")
    public abstract DocumentDTO toResponse(Document doc);

    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, qualifiedByName = "merge")
    public abstract Document merge(@MappingTarget Document oldDoc, DocumentDTO newDoc);

    public abstract List<DocumentDTO> toResponse(List<Document> all);
}
