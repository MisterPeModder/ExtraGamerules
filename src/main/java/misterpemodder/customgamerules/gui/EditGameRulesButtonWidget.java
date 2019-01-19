package misterpemodder.customgamerules.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.widget.ButtonWidget;

public class EditGameRulesButtonWidget extends ButtonWidget {
  public final Gui containingGui;

  public EditGameRulesButtonWidget(Gui containingGui, int id, int x, int y, String text) {
    super(id, x, y, text);
    this.containingGui = containingGui;
  }

  @Override
  public void onPressed(double x, double y) {
    MinecraftClient.getInstance().openGui(new EditGameRulesGui(this.containingGui));
  }
}
