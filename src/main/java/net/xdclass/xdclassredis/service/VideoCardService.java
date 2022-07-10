package net.xdclass.xdclassredis.service;

import net.xdclass.xdclassredis.model.VideoCardDO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VideoCardService {

    List<VideoCardDO> list();
}
