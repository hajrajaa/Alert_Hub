package com.mst.loaderservice.service;

import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.repository.PlatformInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcessorService {

    @Autowired
    PlatformInformationRepository repo;

    public boolean actionCheckIfThresholdMet(Label label, int threshold, int timeFrameHours) {

        LocalDateTime from = LocalDateTime.now().minusHours(timeFrameHours);
        int count = repo.countByLabelAndUpdatedAtGreaterThan(label,from);
        return count >= threshold;

    }
}
