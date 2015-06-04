/*
 * Druid - a distributed column store.
 * Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.druid.server.coordinator.rules;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.druid.timeline.DataSegment;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.Map;

/**
 */
public class ForeverLoadRule extends LoadRule
{
  private final Map<String, Integer> tieredReplicants;

  @JsonCreator
  public ForeverLoadRule(
      @JsonProperty("tieredReplicants") Map<String, Integer> tieredReplicants
  )
  {
    validateTieredReplicants(tieredReplicants);
    this.tieredReplicants = tieredReplicants;
  }

  @Override
  @JsonProperty
  public String getType()
  {
    return "loadForever";
  }

  @Override
  @JsonProperty
  public Map<String, Integer> getTieredReplicants()
  {
    return tieredReplicants;
  }

  @Override
  public int getNumReplicants(String tier)
  {
    Integer retVal = tieredReplicants.get(tier);
    return (retVal == null) ? 0 : retVal;
  }

  @Override
  public boolean appliesTo(DataSegment segment, DateTime referenceTimestamp)
  {
    return true;
  }

  @Override
  public boolean appliesTo(Interval interval, DateTime referenceTimestamp)
  {
    return true;
  }
}
