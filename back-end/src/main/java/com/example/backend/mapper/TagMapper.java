package com.example.backend.mapper;

import com.example.backend.model.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author szs
* @description 针对表【tag(标签)】的数据库操作Mapper
* @createDate 2024-10-20 19:10:15
* @Entity generator.domain.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




