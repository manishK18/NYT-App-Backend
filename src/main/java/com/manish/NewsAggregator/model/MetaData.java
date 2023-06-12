package com.manish.NewsAggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
    private int offset;
    private int limit;//shouldn't we change it to count? since we are sending the number of results being returned
}
