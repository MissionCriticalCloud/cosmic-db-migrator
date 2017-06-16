package com.cloud.upgrade.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends CrudRepository <Version, Long> {

    Version findFirst1ByOrderByIdDesc();
}
