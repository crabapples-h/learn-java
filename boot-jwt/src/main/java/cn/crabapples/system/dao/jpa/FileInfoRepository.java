package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.BaseRepository;
import cn.crabapples.system.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
}
