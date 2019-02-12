package com.misterpemodder.extragamerules.hook;

import com.misterpemodder.extragamerules.ExtraGameRuleValues;

public interface WorldHook {
  public ExtraGameRuleValues getEGValues();

  public void setEGValues(ExtraGameRuleValues egValues);
}
