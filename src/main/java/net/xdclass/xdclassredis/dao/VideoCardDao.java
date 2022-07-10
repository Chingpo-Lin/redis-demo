package net.xdclass.xdclassredis.dao;

import net.xdclass.xdclassredis.model.VideoCardDO;
import net.xdclass.xdclassredis.model.VideoDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class VideoCardDao {

    /**
     * mimic the process of getting data from db
     * @return
     */
    public List<VideoCardDO> list() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<VideoCardDO> cardDOList = new ArrayList<>();

        VideoCardDO videoCardDO = new VideoCardDO();
        videoCardDO.setId(1);
        videoCardDO.setTitle("popular video");

        VideoDO videoDO1 = new VideoDO(1, "video1", "img1",1000);
        VideoDO videoDO2 = new VideoDO(2, "video2", "img2",10001);
        VideoDO videoDO3 = new VideoDO(3, "video3", "img3",100011);
        VideoDO videoDO4 = new VideoDO(4, "video4", "img4",1000111);
        List<VideoDO> videoDOList = new ArrayList<>();
        videoDOList.add(videoDO1);
        videoDOList.add(videoDO2);
        videoDOList.add(videoDO3);
        videoDOList.add(videoDO4);

        videoCardDO.setList(videoDOList);

        VideoCardDO videoCardDO2 = new VideoCardDO();
        videoCardDO2.setId(1);
        videoCardDO2.setTitle("popular video2");

        VideoDO videoDO21 = new VideoDO(1, "video21", "img21",1000);
        VideoDO videoDO22 = new VideoDO(2, "video22", "img22",10001);
        VideoDO videoDO23 = new VideoDO(3, "video23", "img23",100011);
        VideoDO videoDO24 = new VideoDO(4, "video24", "img24",1000111);
        List<VideoDO> videoDOList2 = new ArrayList<>();
        videoDOList2.add(videoDO21);
        videoDOList2.add(videoDO22);
        videoDOList2.add(videoDO23);
        videoDOList2.add(videoDO24);

        videoCardDO2.setList(videoDOList2);
        cardDOList.add(videoCardDO);
        cardDOList.add(videoCardDO2);
        return cardDOList;
    }
}
