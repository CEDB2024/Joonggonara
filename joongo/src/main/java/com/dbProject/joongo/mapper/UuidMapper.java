package com.dbProject.joongo.mapper;

import com.dbProject.joongo.domain.Uuid;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UuidMapper {

    Long createUuid(Uuid uuid);

    Uuid findById(Long id);
}
