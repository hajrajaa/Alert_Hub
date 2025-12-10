package com.mst.loaderservice.service;

import com.mst.loaderservice.dto.LabelCountAggregateResponse;
import com.mst.loaderservice.enums.Label;
import com.mst.loaderservice.repository.PlatformInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformInformationService {
    @Autowired
    private PlatformInformationRepository repo;

    public Long getDeveloperWithMostLabelOccurrences(Label label, int since){
        List<Object[]> res = repo.getDevWithMostOccByLabel(label, LocalDateTime.now().minusDays(since));
        if(!res.isEmpty()){
            return (Long)((res.getFirst())[0]);
        }
        return (long) -1;
    }

    public Map<String, Long> developerLabelAggregate(Long developerId, int since) {
        List<LabelCountAggregateResponse> res = repo.developerLabelAggregate(developerId,LocalDateTime.now().minusDays(since));
        Map<String, Long> resMap = new HashMap<>();
        res.forEach(row -> resMap.put(row.label().name(),row.count()));
        return resMap;
    }

    public int developerTaskAmount(Long developerId, int since) {
        int res = repo.countByAssigneeIdAndUpdatedAtGreaterThan(developerId,
                LocalDateTime.now().minusDays(since));
        return res;
    }


}
