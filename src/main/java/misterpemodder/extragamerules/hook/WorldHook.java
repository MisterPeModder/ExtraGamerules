package misterpemodder.extragamerules.hook;

import misterpemodder.extragamerules.ExtraGameRuleValues;

public interface WorldHook {
  public ExtraGameRuleValues getEGValues();

  public void setEGValues(ExtraGameRuleValues egValues);
}
