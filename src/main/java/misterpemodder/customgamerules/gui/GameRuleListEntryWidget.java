package misterpemodder.customgamerules.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.world.GameRules;

public class GameRuleListEntryWidget extends EntryListWidget.Entry<GameRuleListEntryWidget> {
  public final String key;
  public final GameRuleListWidget list;
  private final MinecraftClient client;

  public GameRuleListEntryWidget(String key, GameRules.Value value, MinecraftClient client,
      GameRuleListWidget list) {
    this.key = key;
    this.client = client;
    this.list = list;
  }

  @Override
  public void draw(int width, int height, int var3, int var4, boolean selected, float delta) {
    if (this.list.selected == this) {
      int x = getX();
      int y = getY();
      Drawable.drawRect(x - 2, y - 2, x - 2 + width - 15, y - 2 + 18, 0xFF808080);
      Drawable.drawRect(x - 1, y - 1, x - 3 + width - 15, y - 3 + 18, 0xFF000000);
    }
    this.client.fontRenderer.draw(this.key, this.getX() + 32 + 3, this.getY() + 1, 0xffffff);
  }

  @Override
  public int getX() {
    return super.getX();
  }

  @Override
  public int getY() {
    return super.getY();
  }

  @Override
  public boolean mouseClicked(double v, double v1, int i) {
    list.selected = this;
    return true;
  }
}
