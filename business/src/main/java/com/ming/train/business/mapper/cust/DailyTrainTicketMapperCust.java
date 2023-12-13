package com.ming.train.business.mapper.cust;

import java.util.Date;

/**
 * @author clownMing
 */
public interface DailyTrainTicketMapperCust {

    void updateCountBySell(
            Date date,
            String trainCode,
            String seatTypeCode,
            Integer minStartIndex,
            Integer maxStartIndex,
            Integer minEndIndex,
            Integer maxEndIndex
    );
}
