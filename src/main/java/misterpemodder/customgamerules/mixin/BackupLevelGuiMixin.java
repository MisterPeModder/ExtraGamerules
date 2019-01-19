package misterpemodder.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.customgamerules.gui.EditGameRulesButtonWidget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.menu.BackupLevelGui;
import net.minecraft.client.resource.language.I18n;


@Mixin(BackupLevelGui.class)
public abstract class BackupLevelGuiMixin extends Gui {
  @Inject(at = @At("TAIL"), method = "onInitialized()V")
  protected void onOnInitialized(CallbackInfo ci) {
    this.addButton(new EditGameRulesButtonWidget(this, 42, this.width / 2 - 100,
        this.height / 4 + 168 + 5, I18n.translate("customgamerules.edit")));
  }
}
